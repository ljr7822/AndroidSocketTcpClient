package com.example.sockettcpclient.Enums;

/**
 * 消息数据类型的枚举
 * author : Iwen大大怪
 * create : 2020/10/24 17:08
 */
public enum MsgEnum {

    TYPE_RECEIVE(0,"接收"),

    TYPE_SEND(1,"发送"),

    TYPE_SYS(2,"系统")

    ;
    Integer code;

    String desc;

    MsgEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
