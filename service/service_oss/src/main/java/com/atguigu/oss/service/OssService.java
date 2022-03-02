package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传头像方法
    String uploadOssFile(MultipartFile file);

}
