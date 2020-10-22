package com.example.sockettcpclient.tcpclient;

import android.util.Log;

import com.example.sockettcpclient.activitys.BaseActivity;
import com.example.sockettcpclient.adapter.Msg;
import com.example.sockettcpclient.utils.Prompt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * socket tcp 封装类
 * <p>
 * author : Iwen大大怪
 * date   : 2020/10/2215:56
 */
public class TcpClient extends BaseActivity {
    protected Socket socket;
    protected Prompt mPrompt = new Prompt();
    BufferedReader re = null;
    ObjectInputStream ois = null;
    Object socketMsg = null;

    /**
     * 连接socket
     */
    public void createSock(final String ServerIp, final int ServerPort) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress inetAddress = InetAddress.getByName(ServerIp);
                    socket = new Socket(inetAddress, ServerPort);
                    AcceptMsg();
                    Log.e("提示", "run: 连接成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("提示", "run: 连接失败" + e);
                }
            }
        }).start();
    }

    /**
     * 发送消息方法
     *
     * @param msg 传入消息
     * @return 是否发送成功
     */
    public boolean sendMsg(final String msg) {
        // isConnected():如果套接字已成功连接到服务器，则为true
        if (socket != null && socket.isConnected()) {
            // 说明已经连接成功
            if (!msg.equals("")) {
                // 发送的消息不为空,启动线程来发送消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return true;
            }
        }
        return false;
    }

    /**
     * 接收消息
     */
    public void AcceptMsg() {
        // 判断状态
        if (socket.isConnected() && !socket.isClosed()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ois = new ObjectInputStream(socket.getInputStream());
                        while ((socketMsg = ois.readObject())!=null){
                            String msg = (String) ois.readObject();
                            Msg m;
                            System.out.println(ois.readObject());
                            System.out.println(msg);
                            Log.e("接收:",msg);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
