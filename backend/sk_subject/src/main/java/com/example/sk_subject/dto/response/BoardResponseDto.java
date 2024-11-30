package com.example.sk_subject.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String authorName;
    private int view;
    private boolean hasAttachment; // 첨부파일 유무 필드
    private String createdAt;
}
