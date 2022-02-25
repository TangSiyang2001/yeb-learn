package com.tsy.yebserver.service.impl;

import com.tsy.yebserver.dao.entity.Admin;
import com.tsy.yebserver.service.ISsoService;
import com.tsy.yebserver.utils.JwtUtils;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.LoginParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Steven.T
 * @date 2021/11/13
 */
@Slf4j
@Service
public class SsoServiceImpl implements ISsoService {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Result login(LoginParam loginParam,String correctCaptcha) {
        if(!Objects.equals(loginParam.getCaptcha(),correctCaptcha)){
            return Result.fail(Result.CodeMsg.CAPTCHA_ERROR);
        }
        final String username = loginParam.getUsername();
        final String rawPassword = loginParam.getPassword();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            return Result.fail(Result.CodeMsg.ACCOUNT_PASSWORD_NOT_EXIST);
        }
        if (!userDetails.isEnabled()) {
            return Result.fail(Result.CodeMsg.ACCOUNT_DISABLED);
        }
        //更新security登录用户对象
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token = JwtUtils.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>(2);
        //前端到时要把head放在请求header中
        tokenMap.put("head", JwtUtils.getTokenHead());
        tokenMap.put("token", token);
        return Result.success(tokenMap);
    }

    @Override
    public Authentication getLoginAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Admin getLoginAdmin() {
        return (Admin) getLoginAuthentication().getPrincipal();
    }


    @Override
    public void setLoginAuthentication(HttpServletRequest request, UserDetails userDetails){
        var authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
