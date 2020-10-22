package com.example.sockettcpclient.dao;

/**
 * author : Iwen大大怪
 * date   : 2020/10/231:12
 */
public class InputDao {
    public String type; // 消息类型 3-连接
    public String ip; // ip地址
    public String port; // 端口号
    public String username; // 用户名
    public String msg; // 消息

    public InputDao(String type, String ip, String port, String username, String msg) {
        this.type = type;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
