package com.tsy.yebserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.dao.mapper.AdminMapper;
import com.tsy.yebserver.service.IAdminService;
import com.tsy.yebserver.service.ISsoService;
import com.tsy.yebserver.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private ISsoService ssoService;

    @Override
    public Admin getAdminInfoByUsername(String username) {
        return adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username)
                .eq(Admin::isEnabled, true));
    }

    @Override
    public Result getAdminByKeywords(String keywords) {
        final Integer adminId = (ssoService.getLoginAdmin()).getId();
        return Result.success(adminMapper.getAdminByKeywords(adminId,keywords));
    }
}
