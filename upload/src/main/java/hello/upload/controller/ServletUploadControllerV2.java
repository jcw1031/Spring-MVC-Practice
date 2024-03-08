package hello.upload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
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
                .flatMap(part -> part.getHeaderNames().stream()
                        .peek(headerName -> log.info("header = {} : {}", headerName, part.getHeader(headerName))));

        return "upload-form";
    }
}
