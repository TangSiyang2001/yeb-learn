package com.tsy.yebserver.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/11/13
 */
@Data
@ApiModel(value = "登录vo",description = "登录vo")
public class LoginParam {

    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @ApiModelProperty(value = "验证码",required = true)
    private String captcha;

}
