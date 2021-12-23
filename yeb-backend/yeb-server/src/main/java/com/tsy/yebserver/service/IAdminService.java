package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户vo
     */
    Admin getAdminInfoByUsername(String username);

    /**
     * 根据关键词获取管理员
     * @param keywords 关键词
     * @return 结果
     */
    Result getAdminByKeywords(String keywords);

    /**
     * 更新操作员角色
     * @param adminId 管理员id
     * @param roleIds 角色id列表
     * @return 结果
     */
    Result updateAdminRoles(Integer adminId, Integer[] roleIds);
}
