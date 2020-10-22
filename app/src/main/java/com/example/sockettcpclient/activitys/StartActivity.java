package com.example.sockettcpclient.activitys;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.sockettcpclient.R;
import com.example.sockettcpclient.dao.InputDao;
import com.example.sockettcpclient.databinding.ActivityStartBinding;
import com.example.sockettcpclient.utils.Prompt;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StartActivity extends BaseActivity {
    private ActivityStartBinding mBinding;
    private RadioGroup mChoose;
    private EditText mInputIp;
    private EditText mInputPort;
    private Button mStartLinkBtn;
    private String inChoose;
    private String inIp;
    private int inPort;
    private Prompt mPrompt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityStartBinding.inflate(getLayoutInflater());
        // 全屏，隐藏状态栏
        isNoState(true);
        setContentView(mBinding.getRoot());
        init();

        /**
         * 通讯方式选择的点击事件
         */
        mChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    // 选择TCP
                    case R.id.tcp_link_btn:
                        inChoose = "TCP";
                        break;
                    // 选择UDP
                    case R.id.udp_link_btn:
                        inChoose = "UDP";
                        break;
                    default:
                        inChoose = "";
                        break;
                }
                mPrompt.setToast(StartActivity.this, "当前通信方式为" + inChoose);
            }
        });

        /**
         * 确认连接的按钮点击事件
         * TextUtils.isEmpty(mInputPort.getText().toString())
         */
        mStartLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先判断是否都有选择
                if (TextUtils.isEmpty(inChoose)) {
                    // 没有选择通讯方式
                    mPrompt.setToast(StartActivity.this, "请选择通讯方式!");
                } else if (TextUtils.isEmpty(mInputIp.getText().toString())) {
                    // 没有输入ip
                    mPrompt.setToast(StartActivity.this, "请输入ip地址!");
                } else if (TextUtils.isEmpty(mInputPort.getText().toString())) {
                    // 没有输入端口号
                    mPrompt.setToast(StartActivity.this, "请输入端口号!");
                } else {
                    mPrompt.setToast(StartActivity.this, "正在连接...");
                    String changJson = ChangJson("3",mInputIp.getText().toString(),mInputPort.getText().toString(),"iwen","你好");
                    writeObjInSp(changJson);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * 将对象写入手机内存
     *
     * @param object 对象
     */
    private void writeObjInSp(String object) {
        // 将配置写入手机中
        JsonObject jsonObject = new JsonParser().parse(object).getAsJsonObject();
        SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("type", jsonObject.get("type").getAsString());
        editor.putString("ip", jsonObject.get("ip").getAsString());
        editor.putString("port",jsonObject.get("port").getAsString());
        editor.apply();
    }

    /**
     * 将输入的参数封装成对象
     *
     * @param type 通讯类型
     * @param ip   ip地址
     * @param port 端口号
     * @return 封装后的对象
     */
    public String ChangJson(String type, String ip, String port, String username, String msg) {
        Gson gson = new Gson();
        InputDao inputDao = new InputDao(type, ip, port, username, msg);
        String inputJson = gson.toJson(inputDao);
        return inputJson;
    }

    /**
     * 初始化控件
     */
    private void init() {
        mPrompt = new Prompt();
        mChoose = mBinding.chooseGroup;
        mInputIp = mBinding.inputIp;
        mInputPort = mBinding.inputPort;
        mStartLinkBtn = mBinding.linkButton;
    }
}