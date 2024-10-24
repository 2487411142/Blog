package com.limemartini.enums;

public enum AppHttpCodeEnum {
    SUCCESS(200,"success"),
    NEED_LOGIN(401,"need login"),
    NO_OPERATOR_AUTH(403,"no authority"),
    SYSTEM_ERROR(500,"system error"),
    USERNAME_EXIST(501,"the username already exists"),
    PHONENUMBER_EXIST(502,"the phone number already exists"),
    EMAIL_EXIST(503, "the email already exists"),
    NICKNAME_EXIST(505, "nick name already exists"),
    REQUIRE_USERNAME(506, "username is required"),
    REQUIRE_NICKNAME(507, "require nick name"),
    REQUIRE_EMAIL(508, "require email"),
    REQUIRE_PASSWORD(509, "require pasword"),
    LOGIN_ERROR(601,"incorrect username or password"),
    CONTENT_NOT_NULL(602, "the content cannot be empty"),
    FILE_TYPE_ERROR(603, "the file type is wrong");
    final int code;
    final String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}