package com.tsy.service.impl;

import com.tsy.entity.Role;
import com.tsy.mapper.RoleMapper;
import com.tsy.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
