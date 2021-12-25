package com.tsy.yebserver.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.dao.mapper.EmployeeMapper;
import com.tsy.yebserver.service.IEmployeeService;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

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
}
