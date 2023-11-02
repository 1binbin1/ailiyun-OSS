package com.example.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.service.IFileService;
import com.example.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class IFileServiceImpl implements IFileService {

    @Override
    public String upload(MultipartFile file) {
        //工具类获取值
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //存在问题：多次上传相同文件的名称，造成最后一次上传把之前上传文件覆盖
            //解决：方式1.在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileName = uuid + fileName;
            //方式2  把文件按照日期进行分类
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String format = simpleDateFormat.format(date);
            fileName = format +"/" + fileName;

            //调用oss方法实现上传
            //第一个参数：Bucket名称
            //第二个参数：上传到oss文件路径和文件名称
            //第三个参数：上传文件输入流
            ossClient.putObject(bucketName,fileName,inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后的文件路径返回
            return "https://" + bucketName + "."+endpoint + "/" + fileName;
        }catch (Exception e){

            e.printStackTrace();
            return null;

        }
    }
}

