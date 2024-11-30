package com.example.sk_subject.repository;

import com.example.sk_subject.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // Soft Delete가 적용된 글만 조회
    Page<Board> findAllByIsDeletedFalse(Pageable pageable);

    // Soft Delete가 적용된 글만 조회 (단일)
    Board findByIdAndIsDeletedFalse(Long id);
}
