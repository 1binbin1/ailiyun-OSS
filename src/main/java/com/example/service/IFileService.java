package com.example.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author hongxiaobin
 * @Time 2022/11/1-20:26
 */
public interface IFileService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
