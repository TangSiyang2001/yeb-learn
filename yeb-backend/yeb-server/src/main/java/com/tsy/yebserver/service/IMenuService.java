package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.Menu;
import com.tsy.yebserver.vo.MenuVo;
import com.tsy.yebserver.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据管理员id获取菜单
     * @return 菜单视图列表
     */
    List<MenuVo> getMenuByAdminId();

    /**
     * 获取含有菜单和角色的配对列表
     * @return 含有菜单和角色的配对列表
     */
    List<Menu> getMenuWithRoleList();

    /**
     * 列出所有菜单
     * @return 结果
     */
    Result listMenus();


}
