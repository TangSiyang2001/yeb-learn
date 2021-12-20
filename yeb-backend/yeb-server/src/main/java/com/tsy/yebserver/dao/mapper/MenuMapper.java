package com.tsy.yebserver.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsy.yebserver.dao.entity.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据管理员id获取菜单
     * @param id id
     * @return 菜单列表
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 获取含有菜单和角色的配对列表
     * @return 含有菜单和角色的配对列表
     */
    List<Menu> getMenuWithRoleList();

    /**
     * 列出所有菜单
     * @return 结果列表
     */
    List<Menu> listMenus();
}
