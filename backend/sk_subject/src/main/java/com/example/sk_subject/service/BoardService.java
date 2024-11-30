package com.example.sk_subject.service;

import com.example.sk_subject.entity.Board;
import com.example.sk_subject.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 글 작성
    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    // 글 목록 조회 (페이지네이션)
    public Page<Board> getAllBoards(Pageable pageable) {
        return boardRepository.findAllByIsDeletedFalse(pageable);
    }

    // 글 상세 조회
    public Board getBoardById(Long id) {
        Board board = boardRepository.findByIdAndIsDeletedFalse(id);
        if (board == null) {
            throw new RuntimeException("Board not found or deleted.");
        }
        return board;
    }

    // 글 수정
    public Board updateBoard(Long id, Board updatedBoard) {
        Board existingBoard = getBoardById(id);
        existingBoard.setTitle(updatedBoard.getTitle());
        existingBoard.setContent(updatedBoard.getContent());
        return boardRepository.save(existingBoard);
    }

    // 글 삭제 (Soft Delete)
    public void deleteBoard(Long id) {
        Board board = getBoardById(id);
        board.setIsDeleted(true);
        boardRepository.save(board);
    }
}
