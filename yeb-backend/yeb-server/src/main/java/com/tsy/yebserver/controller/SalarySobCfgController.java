package com.tsy.yebserver.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.service.IEmployeeService;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2022/1/10
 */
@Api("SalarySobCfgController")
@RestController
@RequestMapping("/salary/sob_cfg")
public class SalarySobCfgController {

    @Resource
    private IEmployeeService employeeService;

    @ApiOperation("获取所有员工账套")
    @PostMapping
    public Result listSalariesByPage(@RequestBody PageParam pageParam) {
        return employeeService.listEmployeeWithSalariesByPage(pageParam);
    }

    @ApiOperation("修改员工账套")
    @PutMapping
    public Result updateEmployeeSalary(Integer employeeId, Integer salaryId) {
        final boolean isSuccess = employeeService.update(
                new LambdaUpdateWrapper<Employee>()
                        .set(Employee::getSalaryId, salaryId)
                        .eq(Employee::getId, employeeId)
        );
        return isSuccess ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
