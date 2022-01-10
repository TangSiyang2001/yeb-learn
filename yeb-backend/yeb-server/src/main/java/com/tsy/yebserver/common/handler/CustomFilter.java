package com.tsy.yebserver.common.handler;

import com.tsy.yebserver.dao.entity.Menu;
import com.tsy.yebserver.dao.entity.Role;
import com.tsy.yebserver.service.IMenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 权限控制，防止用户通过输入url直接访问菜单
 * 要加Component注解注入才生效
 *
 * @author Steven.T
 * @date 2021/12/1
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Resource
    private IMenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final String requestUrl = ((FilterInvocation) object).getRequestUrl();
        final AntPathMatcher antPathMatcher=new AntPathMatcher();
        final List<Menu> menuWithRoleList = menuService.getMenuWithRoleList();
        for (Menu menu : menuWithRoleList) {
            //若访问路径匹配到了某菜单的url，就将该菜单的角色列表中的所有角色名返回
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                final String[] roleNames = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(roleNames);
            }
        }
        //理解：没有匹配的，默认是登录即可访问，ROLE_LOGIN就是登录以后的默认角色
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return Collections.emptyList();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
