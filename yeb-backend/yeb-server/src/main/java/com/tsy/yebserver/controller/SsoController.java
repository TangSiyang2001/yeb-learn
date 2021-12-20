package com.tsy.yebserver.controller;

import com.tsy.yebserver.service.ISsoService;
import com.tsy.yebserver.vo.Result;
import com.tsy.yebserver.vo.param.LoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Steven.T
 * @date 2021/11/13
 */
@Api(tags = "SsoController")
@RestController
@RequestMapping
public class SsoController {

    @Resource
    private ISsoService ssoService;

    @PostMapping("/login")
    @ApiOperation(value = "登录后返回token")
    public Result login(@RequestBody LoginParam loginParam, HttpServletRequest request){
        final String captcha = (String) request.getSession().getAttribute("captcha");
        return ssoService.login(loginParam,captcha);
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录，前端删除token")
    public Result logout(){
        return Result.success(null);
    }
}
