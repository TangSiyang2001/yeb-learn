package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.service.IEmployeeService;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private IEmployeeService employeeService;

    @PostMapping
    public Result listEmployee(@RequestBody PageParam pageParam, Employee employee, LocalDate[] beginDateScope) {
        return employeeService.listEmployeeByPage(pageParam,employee,beginDateScope);
    }

}
