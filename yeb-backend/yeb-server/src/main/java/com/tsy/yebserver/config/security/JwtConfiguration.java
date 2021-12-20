package com.tsy.yebserver.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Steven.T
 * @date 2021/11/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {

    private String tokenHeader;

    private String secretKey;

    private Long expiration;

    private String tokenHead;

}
