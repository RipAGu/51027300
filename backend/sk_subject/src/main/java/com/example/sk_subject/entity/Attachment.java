package com.example.sk_subject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "attachment") // 테이블 이름 매핑
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary Key 자동 증가
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false) // 외래키 매핑
    private Board board; // 게시글과의 연관 관계

    @Column(nullable = false, length = 255) // 첨부파일 이름
    private String name;

    @Column(nullable = false, length = 255) // 파일 URL
    private String url;

    @Column(nullable = false, length = 255) // 파일 타입 (예: image/jpeg, application/pdf)
    private String type;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // Soft Delete 여부

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
