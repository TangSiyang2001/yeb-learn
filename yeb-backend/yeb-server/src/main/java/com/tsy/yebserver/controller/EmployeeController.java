package com.tsy.yebserver.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.service.*;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Api("EmployeeController")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private IEmployeeService employeeService;

    @Resource
    private IPoliticsStatusService politicsStatusService;

    @Resource
    private IJobLevelService jobLevelService;

    @Resource
    private INationService nationService;

    @Resource
    private IPositionService positionService;

    @Resource
    private IDepartmentService departmentService;

    @ApiOperation("分页获取所有员工")
    @PostMapping
    public Result listEmployee(@RequestBody PageParam pageParam, Employee employee, LocalDate[] beginDateScope) {
        return employeeService.listEmployeeByPage(pageParam, employee, beginDateScope);
    }

    @ApiOperation("添加员工")
    @PostMapping("/add")
    public Result addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @ApiOperation("修改员工")
    @PutMapping
    public Result updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/{id}")
    public Result deleteEmployee(@PathVariable Integer id) {
        return employeeService.removeById(id) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("导出员工")
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportEmployeeTable(HttpServletResponse response) throws IOException {
        response.setHeader(
                "content-disposition",
                "attachment;filename=" + URLEncoder.encode("员工信息表.xls", StandardCharsets.UTF_8)
        );
        final ServletOutputStream outputStream = response.getOutputStream();
        final List<Employee> employees = employeeService.listEmployee();
        final Workbook sheets = ExcelExportUtil.exportExcel(
                new ExportParams("员工信息", "员工信息表", ExcelType.HSSF),
                Employee.class,
                employees
        );
        sheets.write(outputStream);
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
    }


    @ApiOperation("/获取所有政治面貌")
    @GetMapping("/politics_status")
    public Result listPoliticsStatus() {
        return Result.success(politicsStatusService.list());
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/job_levels")
    public Result listJobLevels() {
        return Result.success(jobLevelService.list());
    }

    @ApiOperation("获取所有民族")
    @GetMapping("/nations")
    public Result listNations() {
        return Result.success(nationService.list());
    }

    @ApiOperation("获取所有职位")
    @GetMapping("/positions")
    public Result listPositions() {
        return Result.success(positionService.list());
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/deps")
    public Result listDepartments() {
        return Result.success(departmentService.listDepartmentsByParentId(-1));
    }

    @ApiOperation("获取工号")
    @GetMapping("/new_worker_id")
    public Result getAvailableWorkerId() {
        return employeeService.getAvailableWorkerId();
    }
}
