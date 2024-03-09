package hello.upload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@RequestMapping("/servlet/v2")
@Controller
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String fileDirectory;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV2(MultipartHttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts = {}", parts);

        parts.stream()
                .peek(part -> log.info("name() = {}", part.getName()))
                .forEach(part -> part.getHeaderNames().forEach(headerName ->
                        log.info("header = {} : {}", headerName, part.getHeader(headerName))));

        for (Part part : parts) {
            String name = part.getName();
            log.info("name = {}", name);
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("header = {} : {}", headerName, part.getHeader(headerName));
            }

            // 편의 메서드
            // content-disposition; filename
            log.info("submittedFileName = {}", part.getSubmittedFileName());
            log.info("size = {}", part.getSize()); // part body size

            // 데이터 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body = {}", body);

            // 파일에 저장하기
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDirectory + part.getSubmittedFileName();
                log.info("fullPath = {}", fullPath);
                part.write(fullPath);
            }
        }

        return "upload-form";
    }
}
