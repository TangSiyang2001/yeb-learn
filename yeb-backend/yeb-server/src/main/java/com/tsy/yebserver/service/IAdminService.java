package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.vo.Result;
import org.springframework.security.core.Authentication;

import java.util.List;

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
    List<Admin> getAdminByKeywords(String keywords);

    /**
     * 更新操作员角色
     * @param adminId 管理员id
     * @param roleIds 角色id列表
     * @return 结果
     */
    Result updateAdminRoles(Integer adminId, Integer[] roleIds);

    /**
     * 更新用户密码
     * @param adminId 管理员id
     * @param orgPassword 旧密码
     * @param newPassword 新密码
     * @return 结果
     */
    Result updateAdminPassword(Integer adminId, String orgPassword, String newPassword);

    /**
     * 更新用户信息
     * @param admin 管理员
     * @param authentication 认证信息
     * @return 结果
     */
    Result updateAdminInfo(Admin admin, Authentication authentication);
}
