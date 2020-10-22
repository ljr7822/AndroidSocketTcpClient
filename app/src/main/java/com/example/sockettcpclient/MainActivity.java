package com.example.sockettcpclient;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sockettcpclient.adapter.MsgAdapter;
import com.example.sockettcpclient.databinding.ActivityMainBinding;
import com.example.sockettcpclient.utils.Prompt;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends BaseActivity {

    private Switch mSwitch;
    private Prompt mPrompt;
    private ActivityMainBinding mBinding;
    List<Msg> msgList = new ArrayList<>();
    MsgAdapter adapter;
    Socket socket = null;
    public static boolean connectState = false;
    private ImageView mIvSetting;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // 初始化标题导航栏
        initNavBar(false,"192.168.43.174","8080",true);
        // 状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindow(getWindow());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        mBinding.msgRecyclerView.setAdapter(adapter);

        // 设置页面跳转
        setting(MainActivity.this);


        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mBinding.inputText.getText().toString();
                if ("".equals(content)){
                    // TODO
                }else {
                    Msg msg = new Msg(content,0);
                    msgList.add(msg);
                    adapter.notifyItemChanged(msgList.size()-1);//当有新消息时，刷新ListView中的显示
                    mBinding.msgRecyclerView.scrollToPosition(msgList.size()-1);//将ListView定位到最后一行
                    Log.d("发送--->",msg.getContent());
                    mBinding.inputText.setText("");
                }
            }
        });
    }
}