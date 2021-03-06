package com.example.sockettcpclient.adapter;

/**
 * 自定义信息类，用于存放信息的类型（收or发）以及信息的内容。
 *
 * 我们将文本内容和数据类型传给Msg的一个对象，
 * 之后在别的函数里面读取文本内容和判断依据，
 * 也是对信息包含属性的一种封装，这就是Msg.java的作用
 *
 * author : Iwen大大怪
 * date   : 2020/10/2114:02
 */
public class Msg {
    public static final int TYPE_RECEIVE = 0; // 接收消息的标志
    public static final int TYPE_SEND = 1; // 发送消息的标志
    public static final int TYPE_SYS = 2; // 系统消息的标志
    private String content;
    private int type;
    private String ip;
    private String mUserName;

    public Msg(String content,int type,String ip,String username){
        this.content = content;
        this.type = type;
        this.ip = ip;
        this.mUserName = username;

    }


    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    // 在后面设置文本内容时调用
    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 条件语句的判断依据
    public int getType(){
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
