package com.tsy.yebserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.dao.entity.Menu;
import com.tsy.yebserver.dao.mapper.MenuMapper;
import com.tsy.yebserver.service.IMenuService;
import com.tsy.yebserver.service.ISsoService;
import com.tsy.yebserver.vo.MenuVo;
import com.tsy.yebserver.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private ISsoService ssoService;

    @Override
    public List<MenuVo> getMenuByAdminId() {
        final Admin admin = (Admin) ssoService.getLoginAuthentication().getPrincipal();
        return copyList(menuMapper.getMenusByAdminId(admin.getId()));
    }

    @Override
    public List<Menu> getMenuWithRoleList() {
        return menuMapper.getMenuWithRoleList();
    }

    @Override
    public Result listMenus() {
        return Result.success(copyList(menuMapper.listMenus()));
    }



    private List<MenuVo> copyList(List<Menu> menus){
        List<MenuVo> menuVoList=new ArrayList<>();
        for (Menu menu : menus) {
            final MenuVo menuVo = copy(menu);
            menuVoList.add(menuVo);
        }
        return menuVoList;
    }

    private MenuVo copy(Menu menu){
        MenuVo menuVo=new MenuVo();
        BeanUtils.copyProperties(menu,menuVo);
        final List<Menu> children = menu.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            menuVo.setChildren(copyList(children));
        }
        return menuVo;
    }
}
