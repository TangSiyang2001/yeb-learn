package com.tsy.yebserver.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.MenuRole;
import com.tsy.yebserver.dao.mapper.MenuRoleMapper;
import com.tsy.yebserver.service.IMenuRoleService;
import com.tsy.yebserver.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Resource
    private MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateMenuRole(Integer rid, Integer[] midList) {
        //先全删，再添加
        menuRoleMapper.delete(new LambdaQueryWrapper<MenuRole>().eq(MenuRole::getRid, rid));
        //如果传入为空，说明全删
        if (ArrayUtils.isEmpty(midList)) {
            return Result.success(null);
        }
        final Integer recordNums = menuRoleMapper.reInsert(rid, midList);
        if (Objects.equals(recordNums, midList.length)) {
            return Result.success(null);
        }
        return Result.fail(Result.CodeMsg.OPERATION_FAILED);
    }
}
