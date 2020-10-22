package com.example.sockettcpclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : Iwen大大怪
 * date   : 2020/10/2113:21
 */
public class BaseActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvSetting;
    private TextView mTvPort;


    /**
     * 初始化NavigationBar
     * @param isShowBack 返回按钮
     * @param title 标题
     * @param port 端口号
     * @param isShowSetting 设置按钮
     */
    public void initNavBar (boolean isShowBack, String title, String port, boolean isShowSetting){

        mIvBack = findViewById(R.id.iv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mIvSetting = fd(R.id.iv_setting);
        mTvPort = fd(R.id.tv_port);


        mIvBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mTvTitle.setText(title);
        mTvPort.setText(port);
        mIvSetting.setVisibility(isShowSetting ? View.VISIBLE : View.GONE);

    }

    /**
     * 读取ip
     */
    public String readIp(){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String title = preferences.getString("ip","192.168.43.174");
        return title;
    }

    /**
     * 读取端口
     */
    public int readPort(){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        int title = preferences.getInt("port",8080);
        return title;
    }



    /**
     * 状态栏颜色
     * @param window
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setWindow(Window window) {
        window = getWindow();
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.qqgreen));
    }
    /**
     * 设置按钮
     */
    protected void setting(final Context context){
        mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context ,setActivity.class);
                startActivity(intent);
                Log.e("点击事件","设置");
            }
        });
    }

    /**
     * 返回键
     */
    protected void back(){
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 模拟返回事件（关键）
                Log.e("点击事件","返回");
            }
        });
    }

    /**
     * findViewById
     * @param id
     * @param <T>
     * @return fd到的视图
     */
    protected <T extends View> T fd (@IdRes int id){
        return findViewById(id);
    }
}
