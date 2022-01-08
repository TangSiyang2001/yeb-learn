package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.PageParam;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * 获取当前可用的新用户工号
     * @return 结果
     */
    Result getAvailableWorkerId();

    /**
     * 添加员工
     * @param employee 员工
     * @return 结果
     */
    Result addEmployee(Employee employee);

    /**
     * 修改员工信息
     * @param employee 员工
     * @return 结果
     */
    Result updateEmployee(Employee employee);

    /**
     * 根据id获取员工
     * @param id 如果为空，获取所有
     * @return 员工列表
     */
    List<Employee> getEmployeeById(Integer id);

    /**
     *批量导入员工信息
     *
     * @param employees 员工信息
     * @return 执行结果
     */
    Result saveEmployeeInfoList(List<Employee> employees);
}
