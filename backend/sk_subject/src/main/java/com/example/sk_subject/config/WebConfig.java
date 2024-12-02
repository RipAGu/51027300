package com.example.sk_subject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //경로설정
        String uploadPath = "file:" + System.getProperty("user.dir") + "/uploads/";
        System.out.println("uploadPath: " + uploadPath);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/")
                .addResourceLocations("file:backend/sk_subject/uploads/");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5137", "http://localhost:5173") // Vue 서버 출처
                .allowedMethods("*") // 모든 HTTP 메서드 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true); // 인증 정보(쿠키, Authorization 헤더) 허용
    }
}
