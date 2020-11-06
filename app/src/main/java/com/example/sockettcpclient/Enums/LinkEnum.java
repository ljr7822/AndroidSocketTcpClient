package com.example.sockettcpclient.Enums;

/**
 * author : Iwen大大怪
 * create : 2020/10/24 17:15
 */
public enum LinkEnum {

    TCP_LINK(0,"TCP"),

    UDP_LINK(1,"UDP"),

    ;

    Integer code;

    String desc;

    LinkEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
