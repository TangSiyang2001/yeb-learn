package com.tsy.yebserver.config.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Steven.T
 * @date 2021/11/19
 */
@Configuration
public class CaptchaConfiguration {

    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha dk = new DefaultKaptcha();
        Properties properties = new Properties();

        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "yellow");
        //干扰线颜色
        properties.setProperty("kaptcha.noise.color","blue");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "110");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // session key
        properties.setProperty("kaptcha.session.key", "code");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "Times New Roma,微软雅黑");
        //加鱼眼效果
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.FishEyeGimpy");
        //加水纹效果
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.WaterRipple");
        //加阴影效果
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        //配置
        Config config = new Config(properties);
        //将配置信息放入DefaultKaptcha
        dk.setConfig(config);
        return dk;
    }
}
