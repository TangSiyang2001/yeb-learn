package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Role;
import com.tsy.yebserver.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IRoleService extends IService<Role> {
    /**
     * 根据管理员id获取角色列表
     * @param adminId 管理员id
     * @return 角色列表
     */
    List<Role> listRolesByAdminId(Integer adminId);

    /**
     * 获取所有角色
     * @return 结果
     */
    Result listRoles();
}
