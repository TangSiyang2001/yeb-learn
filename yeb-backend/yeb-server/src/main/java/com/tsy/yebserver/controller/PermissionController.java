package com.tsy.yebserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsy.yebserver.dao.entity.MenuRole;
import com.tsy.yebserver.dao.entity.Role;
import com.tsy.yebserver.service.IMenuRoleService;
import com.tsy.yebserver.service.IMenuService;
import com.tsy.yebserver.service.IRoleService;
import com.tsy.yebserver.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组接口
 *
 * @author Steven.T
 * @date 2021/12/16
 */
@Api("PermissionController")
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;

    @Resource
    private IMenuRoleService menuRoleService;

    @ApiOperation("列出所有角色")
    @GetMapping("/role")
    public Result listRoles() {
        return roleService.listRoles();
    }

    @ApiOperation("添加角色")
    @PostMapping("/role")
    public Result addRole(@RequestBody Role role) {
        return roleService.save(role) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/role/{rid}")
    public Result deleteRoleByRoleId(@PathVariable Integer rid) {
        return roleService.removeById(rid) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("修改角色")
    @PutMapping("/role")
    public Result updateRoleById(@RequestBody Role role) {
        return roleService.updateById(role) ? Result.success(null) : Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }

    @ApiOperation("查询所有菜单")
    @GetMapping("/menu")
    public Result listMenus() {
        return menuService.listMenus();
    }

    /**
     * 前端查到所有菜单的情况下，用菜单id就能在前端获取特定角色的菜单
     *
     * @param rid 角色id
     * @return 结果
     */
    @ApiOperation("用角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public Result getMenuIdByRoleId(@PathVariable Integer rid) {
        final List<Integer> menuIdList = menuRoleService
                .list(new LambdaQueryWrapper<MenuRole>().eq(MenuRole::getRid,rid))
                .stream()
                .map(MenuRole::getMid)
                .collect(Collectors.toList());
        return Result.success(menuIdList);
    }

    @ApiOperation("更新角色菜单")
    @PutMapping("/menu")
    public Result updateRoleMenu(Integer rid,Integer[] midList){
        //修改关联表即可
        return menuRoleService.updateMenuRole(rid, midList);
    }
}
