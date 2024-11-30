package com.example.sk_subject.repository;

import com.example.sk_subject.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByBoardIdAndIsDeletedFalse(Long boardId);
    // 특정 게시글에 첨부파일이 있는지 확인
    boolean existsByBoardIdAndIsDeletedFalse(Long boardId);
}
