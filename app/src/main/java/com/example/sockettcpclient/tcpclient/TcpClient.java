package com.example.sockettcpclient.tcpclient;

import android.util.Log;

import com.example.sockettcpclient.activitys.BaseActivity;
import com.example.sockettcpclient.utils.Prompt;

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

    /**
     * 连接socket
     */
    public void createSock(final String ServerIp, final int ServerPort){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    InetAddress inetAddress = InetAddress.getByName(ServerIp);
                    socket = new Socket(inetAddress, ServerPort);
                    Log.e("提示","run: 连接成功");
                }catch (Exception e) {
                    e.printStackTrace();
                    Log.e("提示", "run: 连接失败");
                }
            }
        }).start();
    }
}
