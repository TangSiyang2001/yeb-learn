package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Salary;
import com.tsy.yebserver.service.ISalaryService;
import com.tsy.yebserver.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Api("SalaryController")
@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Resource
    private ISalaryService salaryService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("/sob")
    public Result listSalary(){
        return Result.success(salaryService.list());
    }

    @ApiOperation("添加账套")
    @PostMapping("/sob")
    public Result addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        return salaryService.save(salary) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("更新账套")
    @PutMapping("/sob")
    public Result updateSalary(@RequestBody Salary salary){
        return salaryService.updateById(salary) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("删除账套")
    @DeleteMapping("/sob/{id}")
    public Result deleteSalary(@PathVariable Integer id){
        return salaryService.removeById(id) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
