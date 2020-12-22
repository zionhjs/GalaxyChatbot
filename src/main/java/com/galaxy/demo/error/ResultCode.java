package com.galaxy.demo.error;

public enum ResultCode {
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),

    USER_LOGIN_CHANNEL_ERROR(800010),
    VERFIY_CODE_TIME_ERROR(800009),
    VERFIY_CODE_ERROR(800008),
    VERFIY_TOKEN_ERROR(800007),
    PASSWORD_ERROR(800006),
    USER_NOT_EXIST(800005),
    NOT_EXIST_TOKEN_EXCEPTION(800004),
    NOT_EXIST_USER_EXCEPTION(800003),
    OUT_TIME_TOKEN(800002),
    NOT_LOGIN_EXCEPTION(800001)
    ;

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
