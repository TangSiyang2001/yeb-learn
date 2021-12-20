package com.tsy.yebserver.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsy.yebserver.dao.entity.MenuRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer reInsert(Integer rid, Integer[] midList);
}
