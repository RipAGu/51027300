package com.example.sk_subject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardDetailResponseDto {
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private List<AttachmentResponseDto> attachments;
    private boolean isMyPost; // 자신이 작성한 게시글 여부
}

