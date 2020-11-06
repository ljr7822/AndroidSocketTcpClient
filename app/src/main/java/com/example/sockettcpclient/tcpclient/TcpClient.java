package com.example.sockettcpclient.tcpclient;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sockettcpclient.base.BaseActivity;
import com.example.sockettcpclient.adapter.Msg;
import com.example.sockettcpclient.dao.InputDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * socket tcp 封装类
 * <p>
 * author : Iwen大大怪
 * date   : 2020/10/2215:56
 */
public class TcpClient extends BaseActivity {
    protected Socket socket;
    DataUpdateListener mListener;
    List<Msg> msgList;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Msg content = (Msg) msg.obj;
                if (mListener != null) {
                    mListener.receiveData(content);
                }
            }
        }
    };

    public void setmListener(DataUpdateListener mListener) {
        this.mListener = mListener;
    }

    public interface DataUpdateListener {
        void receiveData(Msg data);
    }

    /**
     * 连接socket
     */
    public void createSock(final String ServerIp, final int ServerPort, final List<Msg> msgList) {
        this.msgList = msgList;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress inetAddress = InetAddress.getByName(ServerIp);
                    Log.d("ljr", "run: 开始连接");
                    socket = new Socket( inetAddress,ServerPort);
                    Log.d("ljr", "run: 连接成功111");
                    AcceptMsg();
                    Log.d("ljr", "run: 连接成功222");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ljr", "run: 连接失败" + e);
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
    public boolean sendMsg(final String msg, final String mIp, final String mUserName) {
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
                            String changJson = ChangJson(1, mIp, "", mUserName, msg);
                            oos.writeObject(changJson);
                            oos.flush();
                            Log.d("ljr", "发送的数据-->" + changJson);
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
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        while (true) {
                            String accept = (String) ois.readObject();
                            InputDao inputDao = FromJson(accept);
                            // msg = inputDao.msg;
                            Log.d("ljr", "接收到的数据--->" + accept);
                            Log.d("ljr", "解析出来的数据--->" + inputDao.msg);
                            Msg acceptMsg = new Msg(inputDao.msg, 0, inputDao.ip, inputDao.username);
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = acceptMsg;
                            mHandler.sendMessage(msg);
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