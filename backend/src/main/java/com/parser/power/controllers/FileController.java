package com.parser.power.controllers;

import com.parser.power.models.ConvertType;
import com.parser.power.services.FileService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/v1")
@CrossOrigin
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity<byte[]> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("current") ConvertType current,
                                                   @RequestParam("target") ConvertType target,
                                                   HttpServletResponse response) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("blob")
                .build());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return new ResponseEntity<>(fileService.convertFile(file, current, target), headers, HttpStatus.OK);
    }
}
