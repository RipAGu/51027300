package com.example.sk_subject.controller;

import com.example.sk_subject.dto.response.BoardResponseDto;
import com.example.sk_subject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/all")
    public ResponseEntity<Page<BoardResponseDto>> getBoards(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<BoardResponseDto> boards = boardService.getBoards(searchType, keyword, pageable);
        return ResponseEntity.ok(boards);
    }
}
