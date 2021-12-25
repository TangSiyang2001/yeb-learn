package com.tsy.yebserver.common.handler;

import com.tsy.yebserver.service.ISsoService;
import com.tsy.yebserver.utils.JwtUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 不加@Component是因为用@Bean在配置类中注入了
 *
 * @author Steven.T
 * @date 2021/11/17
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private ISsoService ssoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String tokenHeader = JwtUtils.getTokenHeader();
        final String tokenHead = JwtUtils.getTokenHead();
        final String rawContent = request.getHeader(tokenHeader);
        //header不为空且以tokenHead开头时有效
        if (StringUtils.hasLength(rawContent) && rawContent.startsWith(tokenHead)) {
            final String tokenContent = rawContent.substring(tokenHead.length());
            final String username = JwtUtils.getUsernameFromToken(tokenContent);
            //存在username但未登录
            if (StringUtils.hasLength(username) && ssoService.getLoginAuthentication() == null) {
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final boolean isValid = JwtUtils.validateToken(tokenContent, userDetails);
                //若有效就设置用户对象
                if (isValid) {
                    ssoService.setLoginAuthentication(request, userDetails);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
