package com.tsy.yebserver.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.dao.mapper.EmployeeMapper;
import com.tsy.yebserver.service.IEmployeeService;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Result listEmployeeByPage(PageParam pageParam, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page=new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        final List<Employee> records = employeeMapper.listEmployeeByPage(page, employee, beginDateScope).getRecords();
        return Result.success(records);
    }

    @Override
    public Result getAvailableWorkerId() {
        //max(workID)为数据库中用max查找的字段名
        final List<Map<String, Object>> res = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        final int rawId = Integer.parseInt(String.valueOf(res.get(0).get("max(workID)")) + 1);
        return Result.success(String.format("%08d", rawId));
    }

    @Override
    public Result addEmployee(Employee employee) {
        //首先要设置合同期限 根据起止日期计算
        setContractTerm(employee);
        //插入信息
        final int insertRecord = employeeMapper.insert(employee);
        return insertRecord == 1 ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @Override
    public Result updateEmployee(Employee employee) {
        setContractTerm(employee);
        return super.updateById(employee) ? Result.success(null) :Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    private void setContractTerm(Employee employee){
        final LocalDate beginContract = employee.getBeginContract();
        final LocalDate endContract = employee.getEndContract();
        final long duration = beginContract.until(endContract, ChronoUnit.DAYS);
        final DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(duration/365.00)));
    }
}
