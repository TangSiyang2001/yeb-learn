package com.tsy.yebserver.controller;


import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.service.IMenuService;
import com.tsy.yebserver.service.ISsoService;
import com.tsy.yebserver.vo.MenuVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping("/menu")
    public List<MenuVo> getMenuByAdminId(){
        return menuService.getMenuByAdminId();
    }
}
