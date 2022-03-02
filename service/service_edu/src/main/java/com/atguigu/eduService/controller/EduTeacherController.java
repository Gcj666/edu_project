package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.entity.vo.TeacherQuery;
import com.atguigu.eduService.service.EduTeacherService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class EduTeacherController {

    //注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    //1、查询讲师表中的所有数据
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }
    //2、讲师逻辑删除功能
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //3、分页查询讲师功能
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageTeacher(@PathVariable long current,
                         @PathVariable long size){
        Page<EduTeacher> page = new Page<>(current,size);
        eduTeacherService.page(page,null);
        //返回总条数
//        try {
//            int i = 1/0;
//        } catch (Exception e) {
//            throw new GuliException(20001,"亲，你的除数不能为0~");
//        }
        long total = page.getTotal();
        //显示记录
        List<EduTeacher> records = page.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    //分页查询条件
    @PostMapping("pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long size,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current,size);


        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //判断查询条件
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();

        if (!StringUtils.isBlank(name)){
            queryWrapper.like("name",name);
        }
        if (level != null){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isBlank(begin)){
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isBlank(end)){
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        //执行分页查询
        eduTeacherService.page(page,queryWrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);
    }

    //添加讲师接口方法
    @PostMapping("addTeacher")
    public R saveTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查询讲师接口方法
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //根据id修改讲师方法
    @PutMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if (update){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

