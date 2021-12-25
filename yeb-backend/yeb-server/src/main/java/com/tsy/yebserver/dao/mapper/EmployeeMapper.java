package com.tsy.yebserver.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsy.yebserver.dao.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 查询所有符合条件的员工并分页
     * @param page 分页对象
     * @param employee 查询信息
     * @param beginDateScope 日期范围
     * @return 结果
     */
    IPage<Employee> listEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee,
                                       @Param("beginDateScope") LocalDate... beginDateScope);
}
