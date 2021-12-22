package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.service.IAdminService;
import com.tsy.yebserver.vo.AdminVo;
import com.tsy.yebserver.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * <p>
 *  前端控制器
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
    public AdminVo getAdminInfo(@NotNull Principal principal){
        final String username = principal.getName();
        final Admin admin = adminService.getAdminInfoByUsername(username);
        AdminVo adminVo = new AdminVo();
        BeanUtils.copyProperties(admin,adminVo);
        return adminVo;
    }

    @ApiOperation("获取操作员")
    @GetMapping
    public Result getAdminByKeywords(String keywords){
        return adminService.getAdminByKeywords(keywords);
    }
}
