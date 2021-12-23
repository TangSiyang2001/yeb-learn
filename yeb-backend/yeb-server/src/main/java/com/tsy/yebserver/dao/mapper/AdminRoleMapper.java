package com.tsy.yebserver.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsy.yebserver.dao.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新操作员角色
     *
     * @param adminId 管理员id
     * @param roleIds 角色id列表
     * @return 结果条数
     */
    Integer addAdminRoles(@Param("adminId") Integer adminId, @Param("roleIds") Integer[] roleIds);
}
