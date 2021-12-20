package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Admin;

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
}
