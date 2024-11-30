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
@Table(name = "board") // 테이블 이름 매핑
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary Key 자동 증가
    private Long id;

    @Column(nullable = false, length = 255) // 제목은 255자로 제한
    private String title;

    @Lob
    @Column(nullable = false) // 긴 텍스트 저장을 위해 @Lob 추가
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false) // 외래키 매핑
    private Account author; // 작성자 (Account 테이블과 연관)

    @Column(nullable = false)
    private int view = 0; // 기본값 0

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // Soft Delete 여부

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
