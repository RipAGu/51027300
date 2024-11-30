package com.example.sk_subject.controller;

import com.example.sk_subject.entity.Board;
import com.example.sk_subject.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 글 작성
    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    // 글 목록 조회
    @GetMapping
    public Page<Board> getAllBoards(Pageable pageable) {
        return boardService.getAllBoards(pageable);
    }

    // 글 상세 조회
    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    // 글 수정
    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board updatedBoard) {
        return boardService.updateBoard(id, updatedBoard);
    }

    // 글 삭제 (Soft Delete)
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }
}
