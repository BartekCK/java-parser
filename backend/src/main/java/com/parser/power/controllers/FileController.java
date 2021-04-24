package com.parser.power.controllers;

import com.parser.power.models.ConvertType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class FileController {

    @PostMapping("/upload")
    String handleFileUpload(@RequestParam("file") MultipartFile file,
                            @RequestParam("current") ConvertType current,
                            @RequestParam("target") ConvertType target) throws IOException {
        System.out.println(new String(file.getBytes()));
        System.out.println(current.toString());
        System.out.println(target.toString());
        return "ala ma kota";
    }
}
