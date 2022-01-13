package com.tsy.yebserver.config.web;

import com.tsy.yebserver.config.security.JwtConfiguration;
import com.tsy.yebserver.utils.JwtUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2022/1/10
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Resource
    private JwtConfiguration jwtConfiguration;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 添加webSocket的endpoint，使前端可通过webSocket连上服务，
     * 即配置webSocket服务地址，并指定是否使用js
     * 即配置前端->后端
     *
     * @param registry 注册信息
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         *添加端点/ws/ep
         * 允许任意跨域
         * 允许SocketJS
         */
        registry.addEndpoint("/ws/ep").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     * 输入管道参数配置
     * 处理JWT，使通信不被授权拦截器拦截
     *
     * @param registration 注册信息
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        /*
         * 判断是否为连接，若是，获取token并设置用户对象
         * 注意，不属于sso的范畴，因为Websocket的登录理论上应该是自成一体的登录，和SsoService无关
         */
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                Assert.notNull(accessor,"accessor should be not null");
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    //如果是连接
                    final String rawToken = accessor.getFirstNativeHeader("Auth-Token");
                    if (StringUtils.hasLength(rawToken)) {
                        //截取tokenHead即Bearer后面的jwt令牌
                        final String authToken = rawToken.substring(jwtConfiguration.getTokenHead().length());
                        final String username = JwtUtils.getUsernameFromToken(authToken);
                        if(StringUtils.hasLength(username)){
                            //登录
                            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                            //验证token是否有效并重新设置用户对象
                            final boolean isValid = JwtUtils.validateToken(authToken,userDetails);
                            if(isValid){
                                final var authenticationToken = new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                accessor.setUser(authenticationToken);
                            }
                        }
                    }
                }
                return message;
            }
        });
    }

    /**
     * 配置消息代理
     * 即配置后端->前端
     *
     * @param registry 配置信息
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置代理域(可配置多个)，配置代理目的地前缀为/queue,可以在配置域上向前端发送消息
        registry.enableSimpleBroker("/queue");
    }
}
