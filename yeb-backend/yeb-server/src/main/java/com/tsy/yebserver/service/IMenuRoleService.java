package com.tsy.yebserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.yebserver.dao.entity.MenuRole;
import com.tsy.yebserver.vo.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface IMenuRoleService extends IService<MenuRole> {
    /**
     * 更新角色、菜单的关联表
     *
     * @param rid     角色id
     * @param midList 菜单id列表
     * @return 结果
     */
    Result updateMenuRole(Integer rid, Integer[] midList);
}
