package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.service.IAdminService;
import com.tsy.yebserver.vo.AdminVo;
import com.tsy.yebserver.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Api(tags = "AdminController")
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;



    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public AdminVo getAdminInfo(@NotNull Principal principal) {
        final String username = principal.getName();
        final Admin admin = adminService.getAdminInfoByUsername(username);
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin, adminVo);
        return adminVo;
    }

    @ApiOperation("修改当前用户信息")
    @PutMapping("/info")
    public Result updateAdminInfo(@RequestBody Admin admin, Authentication authentication) {
        return adminService.updateAdminInfo(admin,authentication);
    }

    @ApiOperation("修改当前用户密码")
    @PutMapping("/info/pwd")
    public Result updateAdminPassword(@RequestBody Map<String,Object> info){
        final String orgPassword =(String) info.get("org_password");
        final String newPassword = (String) info.get("new_password");
        final Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(adminId,orgPassword,newPassword);
    }

    @ApiOperation("获取操作员")
    @GetMapping
    public Result getAdminByKeywords(String keywords) {
        return Result.success(adminService.getAdminByKeywords(keywords));
    }

    @ApiOperation("修改操作员")
    @PutMapping
    public Result updateAdmin(@RequestBody Admin admin) {
        return adminService.updateById(admin) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("删除操作员")
    @DeleteMapping("/{id}")
    public Result deleteAdmin(@PathVariable Integer id) {
        return adminService.removeById(id) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("更新操作员角色")
    @PutMapping("/roles")
    public Result updateAdminRoles(Integer adminId, Integer[] roleIds) {
        return adminService.updateAdminRoles(adminId, roleIds);
    }
}
