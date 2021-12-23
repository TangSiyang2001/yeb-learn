package com.tsy.yebserver.controller;


import com.tsy.yebserver.service.IRoleService;
import com.tsy.yebserver.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@RestController
@RequestMapping("system/admin/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping
    public Result listRoles(){
        return Result.success(roleService.list());
    }

}
