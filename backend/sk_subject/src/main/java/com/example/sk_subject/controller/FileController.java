package com.example.sk_subject.controller;

import com.example.sk_subject.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file") // 단일 엔드포인트 사용
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    /**
     * 파일 업로드 처리
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        // 파일 저장
        String fileName = fileStorageService.storeFile(file);

        // 파일 타입과 URL 반환
        Map<String, String> response = new HashMap<>();
        response.put("type", file.getContentType());
        response.put("url", fileStorageService.getFileUrl(fileName));

        return ResponseEntity.ok(response);
    }

    /**
     * 파일 삭제 처리 (예제: 파일 이름 기반으로 삭제)
     */
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        // 파일 삭제 로직 추가 (기본 예제는 파일 삭제 성공 응답 반환)
        boolean deleted = fileStorageService.deleteFile(fileName);

        if (deleted) {
            return ResponseEntity.ok("File deleted successfully: " + fileName);
        } else {
            return ResponseEntity.status(404).body("File not found: " + fileName);
        }
    }
}
