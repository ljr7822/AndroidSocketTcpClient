package com.example.sockettcpclient.activitys;


import android.os.Bundle;
import android.view.WindowManager;

import com.example.sockettcpclient.R;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏，隐藏状态栏
        isNoState(true);
        setContentView(R.layout.activity_start);
    }
}