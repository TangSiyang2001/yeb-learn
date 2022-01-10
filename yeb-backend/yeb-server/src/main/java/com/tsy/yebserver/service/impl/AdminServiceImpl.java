package com.tsy.yebserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.dao.entity.AdminRole;
import com.tsy.yebserver.dao.mapper.AdminMapper;
import com.tsy.yebserver.dao.mapper.AdminRoleMapper;
import com.tsy.yebserver.service.IAdminService;
import com.tsy.yebserver.service.ISsoService;
import com.tsy.yebserver.vo.Result;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private AdminRoleMapper adminRoleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin getAdminInfoByUsername(String username) {
        return adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username)
                .eq(Admin::isEnabled, true));
    }

    @Override
    public List<Admin> getAdminByKeywords(String keywords) {
        final Integer adminId = (ssoService.getLoginAdmin()).getId();
        return adminMapper.getAdminByKeywords(adminId, keywords);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateAdminRoles(Integer adminId, Integer[] roleIds) {
        //先删掉再重新添
        adminRoleMapper.delete(new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId, adminId));
        final Integer successNums = adminRoleMapper.addAdminRoles(adminId, roleIds);
        if (successNums == roleIds.length) {
            return Result.success(null);
        }
        return Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @Override
    public Result updateAdminPassword(Integer adminId, String orgPassword, String newPassword) {
        final Admin admin = super.getById(adminId);
        final String encodedOrgPassword = admin.getPassword();
        if (StringUtils.hasLength(newPassword) && passwordEncoder.matches(orgPassword, encodedOrgPassword)) {
            admin.setPassword(passwordEncoder.encode(newPassword));
            if (adminMapper.updateById(admin) == 1) {
                return Result.success(null);
            }
        }
        return Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @Override
    public Result updateAdminInfo(Admin admin, Authentication authentication) {
        //防止密码被更新
        final String password = admin.getPassword();
        if (StringUtils.hasLength(password)) {
            final Admin originalInfo = super.getById(admin.getId());
            if (originalInfo != null && !passwordEncoder.matches(password, originalInfo.getPassword())) {
                return Result.fail(Result.CodeMsg.OPERATION_FAILED);
            }
        }
        if (super.updateById(admin)) {
            //重设用户对象
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            admin,
                            null,
                            authentication.getAuthorities())
            );
            return Result.success(null);
        }
        return Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
