package com.example.sockettcpclient.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sockettcpclient.R;
import com.example.sockettcpclient.base.BaseActivity;
import com.example.sockettcpclient.utils.Prompt;

/**
 * 设置类
 * author : Iwen大大怪
 * date   : 2020/10/2118:31
 */
public class SetActivity extends BaseActivity {
    String userName;
    String ip;
    String port;
    TextView userShow;
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
        read();

        // 点击修改配置 :Integer.parseInt(portset.getText().toString())
        mSetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先创建一个对象
                String changConfig = ChangJson(1, ipset.getText().toString(), portset.getText().toString(), "","");
                // 将配置写入手机中
                WriteObjInSp(changConfig);
                // 再读取并显示
                read();
                mPrompt.setToast(SetActivity.this, "修改成功");
            }
        });

        // 点击恢复配置
        mDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先创建对象
                String restoreConfig = ChangJson(1,"192.168.43.174","8080","iwen大大怪","");
                // 恢复一个默认的数据写入手机
                WriteObjInSp(restoreConfig);
                // 再读取并显示
                read();
                mPrompt.setToast(SetActivity.this, "恢复成功");
            }
        });
    }

    /**
     * 读取内存中的配置
     */
    public void read() {
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        ip = preferences.getString("ip", "192.168.43.174");
        port = preferences.getString("port", "8080");
        userName = preferences.getString("username","iwen大大怪");
        userShow.setText("当前指定用户名:" + userName);
        ipshow.setText("当前指定服务器IP:" + ip);
        portshow.setText("当前指定服务器PORT:" + port);
        ipset.setText(ip);
        portset.setText(port + "");
    }

    /**
     * 初始化控件
     */
    private void init() {
        userShow = fd(R.id.show_user);
        ipshow = fd(R.id.show_ip);
        portshow = fd(R.id.show_port);
        ipset = fd(R.id.ip_set);
        portset = fd(R.id.port_set);
        mSetStart = fd(R.id.set_end);
        mDefault = fd(R.id.set_default);
    }
}

