package com.example.sockettcpclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sockettcpclient.utils.Prompt;

/**
 * 设置
 * author : Iwen大大怪
 * date   : 2020/10/2118:31
 */
public class setActivity extends BaseActivity {
    String ip;
    int port;
    TextView ipshow;
    TextView portshow;
    EditText ipset;
    EditText portset;
    Button mSetStart;
    Button mDefault;
    Prompt mPrompt = new Prompt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initNavBar(true, "连接配置", ">在线<", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindow(getWindow());
        }
        init();
        back();

        // 点击修改配置
        mSetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将配置写入手机中
                SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putString("ip", ipset.getText().toString());
                editor.putInt("port", Integer.parseInt(portset.getText().toString()));
                editor.apply();
                read();
                mPrompt.setToast(setActivity.this, "修改成功");
            }
        });

        // 点击恢复配置
        mDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 恢复一个默认的数据写入手机
                SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putInt("port", 8080);
                editor.putString("ip", "192.168.43.174");
                editor.apply();
                read();
                mPrompt.setToast(setActivity.this, "恢复成功");
            }
        });
    }

    /**
     * 读取内存中的配置
     */
    public void read() {
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        ip = preferences.getString("ip", "192.168.43.174");
        port = preferences.getInt("port", 8080);
        ipshow.setText("当前指定服务器IP:" + ip);
        portshow.setText("当前指定服务器PORT:" + port);
        ipset.setText(ip);
        portset.setText(port + "");
    }

    /**
     * 初始化控件
     */
    private void init() {
        ipshow = fd(R.id.show_ip);
        portshow = fd(R.id.show_port);
        ipset = fd(R.id.ip_set);
        portset = fd(R.id.port_set);
        mSetStart = fd(R.id.set_end);
        mDefault = fd(R.id.set_default);
    }
}

