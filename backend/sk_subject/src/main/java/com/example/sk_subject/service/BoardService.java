package com.example.sk_subject.service;

import com.example.sk_subject.dto.response.BoardResponseDto;
import com.example.sk_subject.entity.Board;
import com.example.sk_subject.repository.AttachmentRepository;
import com.example.sk_subject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final AttachmentRepository attachmentRepository;

    public Page<BoardResponseDto> getBoards(String searchType, String keyword, Pageable pageable) {
        Page<Board> boards;

        // 검색 조건에 따라 분기 처리
        if (searchType == null || searchType.isEmpty() || keyword == null || keyword.isEmpty()) {
            boards = boardRepository.findAllByIsDeletedFalse(pageable);
        } else if ("title".equalsIgnoreCase(searchType)) {
            boards = boardRepository.findByTitleContainingAndIsDeletedFalse(keyword, pageable);
        } else if ("username".equalsIgnoreCase(searchType)) {
            boards = boardRepository.findByAccountUsernameAndIsDeletedFalse(keyword, pageable);
        } else {
            throw new IllegalArgumentException("Invalid search type: " + searchType);
        }

        // 게시글 목록을 DTO로 변환하며 첨부파일 유무 확인
        return boards.map(board -> new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getAuthor().getName(),
                board.getView(),
                attachmentRepository.existsByBoardIdAndIsDeletedFalse(board.getId()), // 첨부파일 유무
                board.getCreatedAt().toString()
        ));
    }
}
