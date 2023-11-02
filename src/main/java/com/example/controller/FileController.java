package com.example.controller;

import com.example.service.IFileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file/upload")
@AllArgsConstructor
public class FileController {

    @Autowired
    private final IFileService fileService;

    @PostMapping
    public String upload(MultipartFile file) {
        //获取上传文件 MultipartFile
        //返回上传到oss的路径
        String url = fileService.upload(file);
        return url;
    }
}
