package com.tsy.yebserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author Steven.T
 * @date 2021/11/13
 */
@Data
@AllArgsConstructor
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data) {
        return new Result(true, CodeMsg.SUCCESS.getCode(), CodeMsg.SUCCESS.msg, data);
    }

    public static Result fail(CodeMsg codeMsg) {
        return new Result(false,codeMsg.getCode() , codeMsg.getMsg(), null);
    }

    public boolean isSuccess() {
        return success;
    }

    @AllArgsConstructor
    public enum CodeMsg {
        /**
         * 成功
         */
        SUCCESS(200, "success"),
        PARAMS_ERROR(10001,"参数错误"),
        ACCOUNT_PASSWORD_NOT_EXIST(10002,"用户名或密码错误"),
        ACCOUNT_DISABLED(10003,"用户被禁用"),
        INVALID_TOKEN(10004,"非法token"),
        EXISTED_ACCOUNT(10005,"用户名重复"),
        LACK_OF_PERMISSION(10006,"权限不足"),
        CAPTCHA_ERROR(10007,"验证码错误"),
        UPLOAD_FAIL(20001,"上传文件失败"),
        NOT_LOGIN(90002,"未登录"),
        DATA_NOT_EXISTS(50001,"数据不存在"),
        INTERNAL_SERVER_ERROR(-999,"系统异常"),
        OPERATION_FAILED(-555,"操作失败");

        @Getter
        private final int code;

        @Getter
        private final String msg;
    }
}
