package com.jntt.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnums {
    INVALID_PARAM(400, "参数错误"),
    PASSWORD_NOT_MATCHING(400, "密码错误"),
    USER_NOT_EXIST(404, "用户不存在"),
    USER_CENT_FAIL(404,"用户中心创建失败"),
    USER_EXIST(404, "用户不存在"),
    Pu_NOT_EXIST(404, "公共物品不存在"),
    USERNAME_OR_PASSWORD_ERROR(400, "账号或密码错误"),
    HAS_SIGN(404,"已经签到过一次"),
    GIFT_FAIL(404,"送礼失败"),
    MSG_FAIL(404,"留言失败"),
    SEND_FAIL(404,"送礼失败"),
    ;

    int value;
    String message;
    public int value() {
        return this.value;
    }

    public String message() {
        return this.message;
    }
}
