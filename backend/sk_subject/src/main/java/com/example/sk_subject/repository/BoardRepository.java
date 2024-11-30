package com.example.sk_subject.repository;

import com.example.sk_subject.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 제목으로 글 조회
    Page<Board> findByTitleContainingAndIsDeletedFalse(String keyword, Pageable pageable);

    // 글 작성자로 글 조회
    Page<Board> findByAccountUsernameAndIsDeletedFalse(String username, Pageable pageable);

    // 글 모두 조회
    Page<Board> findAllByIsDeletedFalse(Pageable pageable);


}
