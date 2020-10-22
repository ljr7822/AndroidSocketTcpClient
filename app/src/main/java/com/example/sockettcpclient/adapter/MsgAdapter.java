package com.example.sockettcpclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sockettcpclient.Msg;
import com.example.sockettcpclient.R;

import java.util.List;

/**
 * author : Iwen大大怪
 * date   : 2020/10/2114:01
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> mMsgList;

    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @NonNull
    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);

        if(msg.getType() == Msg.TYPE_RECEIVE){
            // 如果是接收消息
            holder.leftLayout.setVisibility(View.VISIBLE); // 左边对话框出现
            holder.rightLayout.setVisibility(View.GONE); // 右边对话框隐藏
            holder.leftMsg.setText(msg.getContent());
        }else if(msg.getType() == Msg.TYPE_SEND){
            // 如果是发送消息
            holder.rightLayout.setVisibility(View.VISIBLE);// 右边对话框出现
            holder.leftLayout.setVisibility(View.GONE); // 左边对话框隐藏
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout leftLayout;
        ConstraintLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        public ViewHolder(View view){
            super(view);
            leftLayout = (ConstraintLayout)view.findViewById(R.id.left_layout);
            rightLayout = (ConstraintLayout)view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
        }
    }
}
