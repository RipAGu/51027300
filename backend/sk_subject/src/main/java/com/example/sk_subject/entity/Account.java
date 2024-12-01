package com.example.sk_subject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // 기본값 FALSE

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시간 기본값

}