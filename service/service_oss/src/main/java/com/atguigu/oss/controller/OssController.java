package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像方法
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R uploadOssFile(MultipartFile file){
       String url = ossService.uploadOssFile(file);
       return R.ok().data("url",url);
    }
}
