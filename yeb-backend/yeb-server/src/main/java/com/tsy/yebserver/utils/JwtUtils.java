package com.tsy.yebserver.utils;

import com.tsy.yebserver.config.security.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author Steven.T
 * @date 2021/11/13
 */

@Component
public class JwtUtils {

    /*
     * "iss": "admin",          //该JWT的签发者
     * "iat": 1535967430,        //签发时间
     * "exp": 1535974630,        //过期时间
     * "nbf": 1535967430,         //该时间之前不接收处理该Token
     * "sub": "www.admin.com",   //面向的用户
     * "jti": "9f10e796726e332cec401c569969e13e"   //该Token唯一标识
     */

    private static final String TOKEN_ID = "jti";

    private static final String UNDEFINED_STATUS = "undefined";

    private static final String CLAIM_SUBJECT = "sub";

    private static String tokenHeader;

    private static String secretKey;

    private static Long expiration;

    private static String tokenHead;

    @Resource
    private JwtConfiguration configuration;

    /**
     * 根据用户信息生成token
     *
     * @param userDetails SpringSecurity 用户信息存储
     * @return token串
     */
    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        String jti = generateJti(userDetails.getUsername());
        claims.put(TOKEN_ID, jti);
        claims.put(CLAIM_SUBJECT, userDetails.getUsername());
        return generateToken(claims);
    }

    /**
     * 生成加密jti
     *
     * @param rawString 原字符串
     * @return 结果
     */
    private static String generateJti(String rawString) {
        Assert.notNull(rawString, "cannot manage empty string");
        StringBuilder builder = new StringBuilder(rawString + secretKey + "..." + tokenHead + ".."+tokenHeader+".");
        return Arrays.toString(Sha512DigestUtils.sha(builder.reverse().toString()));
    }

    /**
     * 生成token
     *
     * @param claims 荷载
     * @return token串
     */
    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 检验token合法性
     * 1.是否为当前用户
     * 2.是否过期
     *
     * @param token token串
     * @return 判断结果
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        if (!StringUtils.hasLength(token) || Objects.equals(UNDEFINED_STATUS, token)) {
            return false;
        }
        return isCurrentUser(token, userDetails.getUsername()) && isNotExpired(token);
    }

    /**
     * token中信息是否为当前用户
     *
     * @param token    token
     * @param username 用户名
     * @return 判断结果
     */
    private static boolean isCurrentUser(String token, String username) {
        final Claims claimsContent = getClaimsContent(token);
        return Objects.equals(claimsContent.getSubject(), username) ||
                Objects.equals(generateJti(username), claimsContent.get(TOKEN_ID));
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return 判断结果
     */
    public static boolean isNotExpired(String token) {
        //过期期限在现在之前就失效
        return !getClaimsContent(token).getExpiration().before(new Date());
    }

    /**
     * 获取荷载内容
     *
     * @return 荷载
     */
    private static Claims getClaimsContent(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取用户名
     *
     * @param token token
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        //subject即“sub”对应的用户名
        return getClaimsContent(token).getSubject();
    }

    /**
     * 刷新token
     *
     * @param token 原token
     * @return 新token
     */
    public static String refreshToken(String token) {
        if(isNotExpired(token)){
            final Claims claimsContent = getClaimsContent(token);
            //重新生成后时间会自动刷新，内容set成原来的即可
            return generateToken(claimsContent);
        }
        return null;
    }

    public static String getTokenHeader(){
        return tokenHeader;
    }

    public static String getTokenHead(){
        return tokenHead;
    }

    /**
     * 用静态方法设置静态变量
     * @param aTokenHeader -
     * @param aSecretKey -
     * @param aExpiration -
     * @param aTokenHead -
     */
    private static void setProperties(final String aTokenHeader, final String aSecretKey,
                                      final Long aExpiration, final String aTokenHead) {
        tokenHeader = aTokenHeader;
        secretKey = aSecretKey;
        expiration = aExpiration;
        tokenHead = aTokenHead;
    }

    /**
     * 为了能将配置中的信息读入
     */
    @PostConstruct
    private void init() {
        setProperties(configuration.getTokenHeader(), configuration.getSecretKey(),
                configuration.getExpiration(), configuration.getTokenHead());
    }

}
