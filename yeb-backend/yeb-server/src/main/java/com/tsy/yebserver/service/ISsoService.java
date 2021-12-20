package com.tsy.yebserver.service;

import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.LoginParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 单点登录
 * @author Steven.T
 * @date 2021/11/13
 */
public interface ISsoService {

    /**
     * 登录接口
     * @param loginParam 登录信息
     * @return 结果
     */
    Result login(LoginParam loginParam,String correctCaptcha);

    /**
     * 获取登录认证的信息
     * @return 认证信息
     */
    Authentication getLoginAuthentication();

    /**
     * 设置登录认证信息
     * @param request 请求
     * @param userDetails 用户信息
     */
    void setLoginAuthentication(HttpServletRequest request, UserDetails userDetails);
}
