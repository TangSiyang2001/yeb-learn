package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Department;
import com.tsy.yebserver.service.IDepartmentService;
import com.tsy.yebserver.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Api("DepartmentController")
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Resource
    private IDepartmentService departmentService;

    @ApiOperation("获取所有部门")
    @GetMapping("/")
    public Result listDepartments(){
        return departmentService.listDepartmentsByParentId(-1);
    }

    @ApiOperation("添加部门")
    @PutMapping("/")
    public Result addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }
}