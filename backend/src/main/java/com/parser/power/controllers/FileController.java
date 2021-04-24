package com.parser.power.controllers;

import com.parser.power.models.FileExtension;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class FileController {

    @PostMapping("/upload")
    String handleFileUpload(@RequestParam("file") MultipartFile file,
                            @RequestParam("current") FileExtension current,
                            @RequestParam("target") FileExtension target) {
        System.out.println(file.getName());
        System.out.println(current.toString());
        System.out.println(target.toString());
        return "ala ma kota";
    }
}
