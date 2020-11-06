package com.example.sockettcpclient.activitys;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sockettcpclient.adapter.Msg;
import com.example.sockettcpclient.adapter.MsgAdapter;
import com.example.sockettcpclient.base.BaseActivity;
import com.example.sockettcpclient.databinding.ActivityMainBinding;
import com.example.sockettcpclient.tcpclient.TcpClient;
import com.example.sockettcpclient.utils.Prompt;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Prompt mPrompt = new Prompt();
    private ActivityMainBinding mBinding;
    List<Msg> msgList = new ArrayList<>();
    MsgAdapter adapter;
    private Socket socket = null;
    public static boolean connectState = false;
    private String mPort;
    private String mIp;
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mPort = readPort();
        mIp = readIp();
        mUserName = readUserName();
        // 初始化标题导航栏
        initNavBar(false, mIp + ":" + mPort, ">连接中<", true);
        // 状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindow(getWindow());
        }

        // 适配器配置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        mBinding.msgRecyclerView.setAdapter(adapter);

        // 设置页面跳转
        toSetting(MainActivity.this);

        // 新建一个tcp类
        final TcpClient tcpClient = new TcpClient();
        // 设置Handler监听，接收子线程的数据
        tcpClient.setmListener(new TcpClient.DataUpdateListener() {
            @Override
            public void receiveData(Msg data) {
                msgList.add(data);
                adapter.notifyItemChanged(msgList.size() - 1);// 当有新消息时，刷新ListView中的显示
                mBinding.msgRecyclerView.scrollToPosition(msgList.size() - 1);// 将ListView定位到最后一行
                Log.d("ljr", "接收的数据--->" + data.getContent());
            }
        });
        // 判断是否有socket存在，没有就建立连接
        if (socket == null || socket.isClosed() || !socket.isConnected()) {
            readIp(); // 获取ip
            readPort(); // 获取端口号
            // 建立连接
            tcpClient.createSock(readIp(), Integer.parseInt(readPort()), msgList);
            // 更新标题导航栏
            initNavBar(false, mIp + ":" + mPort, ">在线<", true);
            Log.d("ljr", "********************开始连接********************");
        }

        // 消息发送按钮点击事件
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content =mBinding.inputText.getText().toString();
                if ("".equals(content)) {
                    mPrompt.setToast(MainActivity.this, "请输入内容!");
                } else {

                    tcpClient.sendMsg(content, mIp, mUserName);

                    Msg msg = new Msg(content, 1, mIp, mUserName);
                    msgList.add(msg);
                    adapter.notifyItemChanged(msgList.size() - 1);// 当有新消息时，刷新ListView中的显示
                    mBinding.msgRecyclerView.scrollToPosition(msgList.size() - 1);// 将ListView定位到最后一行
                    Log.d("ljr", "输入的数据--->" + msg.getContent());
                    mBinding.inputText.setText("");
                }
            }
        });
    }
}