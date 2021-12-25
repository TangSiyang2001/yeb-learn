package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 查询所有符合条件的员工并分页
     * @param pageParam 分页参数
     * @param employee 查询信息
     * @param beginDateScope 日期范围
     * @return 结果
     */
    Result listEmployeeByPage(PageParam pageParam, Employee employee, LocalDate[] beginDateScope);
}
