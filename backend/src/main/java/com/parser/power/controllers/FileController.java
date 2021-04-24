package com.parser.power.controllers;

import com.parser.power.models.ConvertType;
import com.parser.power.services.FileService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@AllArgsConstructor
public class FileController {

    private FileService fileService;

    @SneakyThrows
    @PostMapping("/upload")
    byte[] handleFileUpload(@RequestParam("file") MultipartFile file,
                            @RequestParam("current") ConvertType current,
                            @RequestParam("target") ConvertType target) {
        return fileService.convertFile(file, current, target);
    }
}
