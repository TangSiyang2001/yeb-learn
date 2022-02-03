package com.tsy.yebserver.config.security;

import com.tsy.yebserver.common.handler.CustomFilter;
import com.tsy.yebserver.common.handler.CustomUrlDecisionManager;
import com.tsy.yebserver.common.handler.JwtAuthenticationFilter;
import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.service.IAdminService;
import com.tsy.yebserver.service.IRoleService;
import com.tsy.yebserver.utils.ResponseUtils;
import com.tsy.yebserver.vo.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/11/14
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private IAdminService adminService;

    @Resource
    private IRoleService roleService;

    @Resource
    private CustomFilter customFilter;

    @Resource
    private CustomUrlDecisionManager customUrlDecisionManager;

    /**
     * 配置，使自定义UserDetailService在security框架内生效
     * <p>
     * auth.userDetailsService():Add authentication based upon the custom that is passed
     * in. It then returns a to allow customization of
     * the authentication.
     *
     * @param auth the custom that is passed in.
     * @throws Exception -
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置网络访问相关权限
     *
     * @param http -
     * @throws Exception -
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf
        http.csrf().disable()
                //用token，无需session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //除了以上均需认证
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customFilter);
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        return object;
                    }
                })
                .and()
                .headers()
                //无需自定义缓存
                .cacheControl();
        //添加自定义登录授权过滤器，添加自定义未登录授权结果返回
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/captcha",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs/**",
                "/ws/**"
        );
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            final Admin admin = adminService.getAdminInfoByUsername(username);
            if (admin != null) {
                admin.setRoles(roleService.listRolesByAdminId(admin.getId()));
            }
            return admin;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * 配置未登录或token失效时的自定义返回结果
     *
     * @return 已实现配置的接口
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) ->
                ResponseUtils.outPutRestfulStringTypeResponse(response, Result.fail(Result.CodeMsg.NOT_LOGIN));
    }

    /**
     * 配置权限不足时的自定义返回
     *
     * @return 已实现配置的接口
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) ->
                ResponseUtils.outPutRestfulStringTypeResponse(response, Result.fail(Result.CodeMsg.LACK_OF_PERMISSION));
    }
}
