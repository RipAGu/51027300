package com.example.sk_subject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class BoardAddResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private String attachmentUrl;
}