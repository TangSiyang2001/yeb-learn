package com.tsy.yebserver.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsy.yebserver.dao.entity.Department;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 根据父id获取部门
     *
     * @param parentId 父id
     * @return 结果列表
     */
    List<Department> listDepartmentsByParentId(Integer parentId);

    /**
     * 添加部门
     * @param department 部门
     */
    void addDepartment(Department department);
}
