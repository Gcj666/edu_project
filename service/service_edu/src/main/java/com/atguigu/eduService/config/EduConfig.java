package com.atguigu.eduService.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.eduService.mapper")
public class EduConfig {

}
