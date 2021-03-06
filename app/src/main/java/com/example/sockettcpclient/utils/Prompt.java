package com.example.sockettcpclient.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

/**
 * author : Iwen大大怪
 * date   : 2020/10/2116:51
 */
public class Prompt {
    private Context mContext;
    private String mText;

    // 封装一个提示方法
    public void setToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 封装一个弹出提示方法
    public void setDialog(
            Context context, final String title,
            String Message, String PositiveButtonText,
            String NegativeButtonText,
            DialogInterface.OnClickListener isListener,
            DialogInterface.OnClickListener noListener){
        AlertDialog.Builder builder  = new AlertDialog.Builder(context);
        builder.setTitle(title) ;
        builder.setMessage(Message) ;
        builder.setPositiveButton(PositiveButtonText, isListener);
        builder.setNegativeButton(NegativeButtonText, noListener);
        builder.show();
    }
}
