package com.davidson.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    @Value("${local.srcFolder}")
    private String dataPath;

    @GetMapping(value = "/img/usr/dataBlog/Articles/{article}/{image}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String article, @PathVariable String image) throws IOException {

        File imgFile = new File(dataPath + "/dataBlog/Articles/" + article + "/" + image);
        InputStream targetStream = new FileInputStream(imgFile);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/img/{image}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String image) throws IOException {
        File imgFile = new File(dataPath + "/dataBlog/Testimonies/images/" + image);
        InputStream targetStream = new FileInputStream(imgFile);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(targetStream));
    }
}
