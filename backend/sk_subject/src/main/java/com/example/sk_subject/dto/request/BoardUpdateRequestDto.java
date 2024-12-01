package com.example.sk_subject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private String attachmentUrl;
}