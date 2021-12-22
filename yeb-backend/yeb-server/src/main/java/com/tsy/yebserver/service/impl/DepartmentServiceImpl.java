package com.tsy.yebserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Department;
import com.tsy.yebserver.dao.mapper.DepartmentMapper;
import com.tsy.yebserver.service.IDepartmentService;
import com.tsy.yebserver.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public Result listDepartmentsByParentId(Integer parentId) {
        return Result.success(departmentMapper.listDepartmentsByParentId(parentId));
    }

    @Override
    public Result addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        if(department.getResult() == 1){
            return Result.success(department);
        }
        return Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteDepartmentById(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        if(department.getResult() == 1){
            return Result.success(null);
        }
        return Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
