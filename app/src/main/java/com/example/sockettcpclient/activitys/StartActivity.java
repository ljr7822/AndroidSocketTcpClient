package com.example.sockettcpclient.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.sockettcpclient.R;
import com.example.sockettcpclient.base.BaseActivity;
import com.example.sockettcpclient.databinding.ActivityStartBinding;
import com.example.sockettcpclient.utils.Prompt;

public class StartActivity extends BaseActivity {
    private ActivityStartBinding mBinding;
    private RadioGroup mChoose;
    private EditText mInputIp;
    private EditText mInputPort;
    private Button mStartLinkBtn;
    private String inChoose;
    private Prompt mPrompt;
    private String mIp ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityStartBinding.inflate(getLayoutInflater());
        // 全屏，隐藏状态栏
        isNoState(true);
        setContentView(mBinding.getRoot());
        initView();

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
                    // 将数据写入数据类
                    String changJson = ChangJson(1, mInputIp.getText().toString(), mInputPort.getText().toString(), "大大怪", "");
                    // 将对象写入手机内存
                    WriteObjInSp(changJson);
                    // 跳转到聊天页面
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView(){
        mPrompt = new Prompt();
        mChoose = mBinding.chooseGroup;
        mInputIp = mBinding.inputIp;
        mInputPort = mBinding.inputPort;
        mStartLinkBtn = mBinding.linkButton;
    }
}