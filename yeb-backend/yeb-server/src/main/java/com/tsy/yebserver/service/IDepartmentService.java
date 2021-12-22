package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Department;
import com.tsy.yebserver.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return 结果
     * @param parentId 父id
     */
    Result listDepartmentsByParentId(Integer parentId);

    /**
     * 添加部门
     * @param department 部门
     * @return 结果
     */
    Result addDepartment(Department department);

    /**
     * 删除部门
     * @param id 部门id
     * @return 结果
     */
    Result deleteDepartmentById(Integer id);
}
