package com.tsy.yebserver.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.tsy.yebserver.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @author Steven.T
 * @date 2021/11/19
 */
@Api(tags = "CaptchaController")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private DefaultKaptcha defaultKaptcha;

    @GetMapping(produces = "image/jpeg")
    @ApiOperation(value = "生成验证码")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        final String text = defaultKaptcha.createText();
        final HttpSession session = request.getSession();
        session.setAttribute("captcha",text);
        final BufferedImage image = defaultKaptcha.createImage(text);
        ResponseUtils.outPutPageTypeResponse(response,image);
    }
}
