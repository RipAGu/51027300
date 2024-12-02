package com.example.sk_subject.controller;

import com.example.sk_subject.dto.request.BoardRequestDto;
import com.example.sk_subject.dto.request.BoardUpdateRequestDto;
import com.example.sk_subject.dto.response.BoardAddResponseDto;
import com.example.sk_subject.dto.response.BoardDetailResponseDto;
import com.example.sk_subject.dto.response.BoardListResponseDto;
import com.example.sk_subject.entity.Account;
import com.example.sk_subject.entity.Board;
import com.example.sk_subject.service.AccountService;
import com.example.sk_subject.service.BoardService;
import com.example.sk_subject.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @GetMapping("/all")
    public ResponseEntity<Page<BoardListResponseDto>> getBoards(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardListResponseDto> boards = boardService.getBoards(searchType, keyword, pageable);
        return ResponseEntity.ok(boards);
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody @Valid BoardRequestDto requestDto, @RequestHeader("Authorization") String token) {
        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer 제거
        Account account = boardService.getAccountByName(username); // Account를 조회하여 가져옴

        // 게시글 생성
        Board board = boardService.createBoard(requestDto, account);
        return ResponseEntity.ok(board); // 생성된 게시글 반환
    }


    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailResponseDto> getBoardDetail(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        String username = null;

        // 토큰이 존재할 경우 사용자 이름 추출
        if (token != null && token.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        }

        // 게시글 상세 정보 가져오기
        BoardDetailResponseDto response = boardService.getBoardDetail(id, username);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDetailResponseDto> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequestDto requestDto,
            @RequestHeader("Authorization") String token) {
        // JWT 토큰에서 사용자 이름 추출
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));

        // 게시글 수정
        BoardDetailResponseDto updatedBoard = boardService.updateBoard(id, requestDto, username);

        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName(); // 토큰에서 사용자 이름 가져오기
        boardService.deleteBoard(id, username);
        return ResponseEntity.ok("게시물이 삭제되었습니다.");
    }

}
