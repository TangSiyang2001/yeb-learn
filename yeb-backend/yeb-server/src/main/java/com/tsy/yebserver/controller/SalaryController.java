package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Salary;
import com.tsy.yebserver.service.ISalaryService;
import com.tsy.yebserver.vo.Result;
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
@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Resource
    private ISalaryService salaryService;

    @GetMapping("/sob")
    public Result listSalary(){
        return Result.success(salaryService.list());
    }

    @PostMapping("/sob")
    public Result addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        return salaryService.save(salary) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @PutMapping("/sob")
    public Result updateSalary(@RequestBody Salary salary){
        return salaryService.updateById(salary) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @DeleteMapping("/sob/{id}")
    public Result deleteSalary(@PathVariable Integer id){
        return salaryService.removeById(id) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
