package com.example.sockettcpclient.activitys;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sockettcpclient.adapter.Msg;
import com.example.sockettcpclient.adapter.MsgAdapter;
import com.example.sockettcpclient.databinding.ActivityMainBinding;
import com.example.sockettcpclient.tcpclient.TcpClient;
import com.example.sockettcpclient.utils.Prompt;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private Switch mSwitch;
    private Prompt mPrompt = new Prompt();
    private ActivityMainBinding mBinding;
    List<Msg> msgList = new ArrayList<>();
    MsgAdapter adapter;
    Socket socket = null;
    public static boolean connectState = false;
    private ImageView mIvSetting;
    private ImageView mIvBack;
    private String mPort;
    private String mIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mPort = String.valueOf(readPort());
        mIp = readIp();
        // 初始化标题导航栏
        initNavBar(false,mIp,mPort,true);
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
        setting(MainActivity.this);


        // 消息发送按钮点击事件
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mBinding.inputText.getText().toString();
                if ("".equals(content)){
                    mPrompt.setToast(MainActivity.this,"请输入内容!");
                }else {
                    Msg msg = new Msg(content,1,mIp);
                    msgList.add(msg);
                    adapter.notifyItemChanged(msgList.size()-1);//当有新消息时，刷新ListView中的显示
                    mBinding.msgRecyclerView.scrollToPosition(msgList.size()-1);//将ListView定位到最后一行
                    Log.d("发送--->",msg.getContent());
                    mBinding.inputText.setText("");
                    TcpClient tcpClient = new TcpClient();
                    tcpClient.createSock("192.168.43.174",9999);
                }
            }
        });
    }

}