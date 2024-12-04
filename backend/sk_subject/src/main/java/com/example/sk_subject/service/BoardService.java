package com.example.sk_subject.service;

import com.example.sk_subject.dto.request.BoardRequestDto;
import com.example.sk_subject.dto.request.BoardUpdateRequestDto;
import com.example.sk_subject.dto.response.AttachmentResponseDto;
import com.example.sk_subject.dto.response.BoardDetailResponseDto;
import com.example.sk_subject.dto.response.BoardListResponseDto;
import com.example.sk_subject.entity.Account;
import com.example.sk_subject.entity.Attachment;
import com.example.sk_subject.entity.Board;
import com.example.sk_subject.repository.AccountRepository;
import com.example.sk_subject.repository.AttachmentRepository;
import com.example.sk_subject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final AttachmentRepository attachmentRepository;
    private final AccountRepository accountRepository;

    public Page<BoardListResponseDto> getBoards(String searchType, String keyword, Pageable pageable) {
        Page<Board> boards;

        // 검색 조건에 따라 분기 처리
        if (searchType == null || searchType.isEmpty() || keyword == null || keyword.isEmpty()) {
            boards = boardRepository.findAllByIsDeletedFalse(pageable);
        } else if ("title".equalsIgnoreCase(searchType)) {
            boards = boardRepository.findByTitleContainingAndIsDeletedFalse(keyword, pageable);
        } else if ("username".equalsIgnoreCase(searchType)) {
            boards = boardRepository.findByAccountNameAndIsDeletedFalse(keyword, pageable);
        } else {
            throw new IllegalArgumentException("Invalid search type: " + searchType);
        }

        // 게시글 목록을 DTO로 변환하며 첨부파일 유무 확인
        return boards.map(board -> new BoardListResponseDto(
                board.getId(),
                board.getTitle(),
                board.getAccount().getName(),
                board.getView(),
                attachmentRepository.existsByBoardIdAndIsDeletedFalse(board.getId()), // 첨부파일 유무
                board.getCreatedAt().toString()
        ));
    }

    @Transactional
    public Board createBoard(BoardRequestDto requestDto, Account account) {
        // 1. Board 엔티티 생성
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .account(account)
                .build();

        // 2. Board를 먼저 저장하여 영속화
        board = boardRepository.save(board);

        // 2. 첨부파일 URL이 있는 경우 처리
        if (requestDto.getAttachmentUrl() != null && !requestDto.getAttachmentUrl().isEmpty()) {
            Attachment attachment = Attachment.builder()
                    .board(board)
                    .name(requestDto.getAttachmentUrl().substring(requestDto.getAttachmentUrl().lastIndexOf('/') + 1)) // 파일 이름 추출
                    .url(requestDto.getAttachmentUrl())
                    .type(detectFileType(requestDto.getAttachmentUrl())) // 파일 타입 감지 로직
                    .build();

            // 첨부파일을 Board에 추가
            board.getAttachments().add(attachment);
        }

        // 3. Board를 저장하면서 연관된 Attachment도 함께 저장됨
        return boardRepository.save(board);
    }

    // 파일 타입 감지 유틸리티 메서드 (간단한 버전)
    private String detectFileType(String url) {
        if (url.endsWith(".png")) return "image/png";
        if (url.endsWith(".jpg") || url.endsWith(".jpeg")) return "image/jpeg";
        if (url.endsWith(".pdf")) return "application/pdf";
        return "unknown";
    }

    // 사용자 계정 조회 (name 필드 사용)
    public Account getAccountByName(String name) {
        return accountRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }


    @Transactional(readOnly = true)
    public BoardDetailResponseDto getBoardDetail(Long id, String username) {
        // 게시글 조회 (존재하지 않을 경우 예외 발생)
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 조회수  증가
        incrementViewCount(id);

        // 첨부파일 정보 추출
        List<AttachmentResponseDto> attachments = board.getAttachments().stream()
                .map(attachment -> new AttachmentResponseDto(
                        attachment.getType(),
                        attachment.getUrl()
                ))
                .toList();

        // 자신이 작성한 게시글인지 확인 (username이 null인 경우 false)
        boolean isMyPost = username != null && board.getAccount().getName().equals(username);

        // DTO로 변환
        return new BoardDetailResponseDto(
                board.getTitle(),
                board.getContent(),
                board.getAccount().getName(),
                board.getCreatedAt().toString(),
                attachments,
                isMyPost
        );
    }

    @Transactional
    public void incrementViewCount(Long boardId) {
        boardRepository.incrementViewCount(boardId);
    }

    @Transactional
    public BoardDetailResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto, String username) {
        // 게시글 조회
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 작성자 확인
        if (!board.getAccount().getName().equals(username)) {
            throw new RuntimeException("게시글을 수정할 권한이 없습니다.");
        }

        // 제목과 내용 업데이트
        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());

        // 첨부파일 업데이트 처리
        if (requestDto.getAttachmentUrl() == null || requestDto.getAttachmentUrl().isEmpty()) {
            // 첨부파일 삭제
            board.getAttachments().clear();
        } else {
            // 첨부파일 추가
            Attachment attachment = new Attachment();
            attachment.setBoard(board);
            attachment.setName(requestDto.getAttachmentUrl().substring(requestDto.getAttachmentUrl().lastIndexOf('/') + 1));
            attachment.setUrl(requestDto.getAttachmentUrl());
            attachment.setType(detectFileType(requestDto.getAttachmentUrl()));

            board.getAttachments().clear(); // 기존 첨부파일 제거
            board.getAttachments().add(attachment);
        }

        // 저장
        Board updatedBoard = boardRepository.save(board);

        // DTO 변환 후 반환
        return new BoardDetailResponseDto(
                updatedBoard.getTitle(),
                updatedBoard.getContent(),
                updatedBoard.getAccount().getName(),
                updatedBoard.getCreatedAt().toString(),
                updatedBoard.getAttachments().stream()
                        .map(attachment -> new AttachmentResponseDto(attachment.getType(), attachment.getUrl()))
                        .toList(),
                true // 본인 게시물 여부
        );
    }

    public void deleteBoard(Long boardId, String username) {
        // 게시글 조회
        Board board = boardRepository.findByIdAndIsDeletedFalse(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 작성자 확인
        if (!board.getAccount().getName().equals(username)) {
            throw new AccessDeniedException("본인의 게시글만 삭제할 수 있습니다.");
        }

        // 삭제 처리
        board.setDeleted(true);
        boardRepository.save(board);
    }


}
