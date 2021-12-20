package com.tsy.yebserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.yebserver.dao.entity.Role;
import com.tsy.yebserver.dao.mapper.RoleMapper;
import com.tsy.yebserver.service.IRoleService;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> listRolesByAdminId(Integer adminId) {
        return roleMapper.listRolesByAdminId(adminId);
    }

    @Override
    public Result listRoles() {
        return Result.success(copyList(list()));
    }

    private List<RoleVo> copyList(List<Role> roleList) {
        List<RoleVo> roleVoList=new ArrayList<>();
        roleList.forEach(role -> {
            RoleVo roleVo=new RoleVo();
            BeanUtils.copyProperties(role,roleVo);
            roleVoList.add(roleVo);
        });
        return roleVoList;
    }


}
