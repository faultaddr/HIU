package com.example.wuyufan.hiu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuyufan.hiu.MainActivity;
import com.example.wuyufan.hiu.R;

import java.util.ArrayList;

/**
 * Created by panyunyi on 2017/3/14.
 * 利用viewHolder做优化，防止加载大量图片时出现OOM
 */

public class ListViewAdapter extends BaseAdapter {

    // adapter 数据初始化
    private Context mContext;
    private ArrayList<String> mUserName = new ArrayList<>();
    private ArrayList<String> mTime = new ArrayList<>();
    private ArrayList<String> mContent = new ArrayList<>();
    private ArrayList<String> mInterest = new ArrayList<>();
    ViewHolder viewHolder;//在handler里处理数据，所以需要把holder对象传出来

    //点赞事件记录器
    private static boolean TAG;


    public ListViewAdapter(Context context, ArrayList<String> userName, ArrayList<String> time, ArrayList<String> content, ArrayList<String> interest) {
        mContext = context;
        mUserName.addAll(userName);
        mTime.addAll(time);
        mContent.addAll(content);
        mInterest.addAll(interest);
        Log.i(">>constuctAdapter", mUserName.size() + "");
    }

    class handler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Intent intent=new Intent();
                    intent.setAction("adapterToactivity");
                    intent.setClass(mContext,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("UserName", msg.arg1 + "");
                    intent.putExtra("UserName", bundle);
                    mContext.startActivity(intent);
            }
        }
    }

    @Override
    public int getCount() {
        Log.i(">>getCount", "success" + " " + mUserName.size());
        return mUserName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i(">>getView", "success" + "  " + i);
        System.out.print("" + i);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.friend_circle_listitem, null);
            holder.imageLike = (ImageView) view.findViewById(R.id.like);
            holder.textContent = (TextView) view.findViewById(R.id.content);
            holder.textTime = (TextView) view.findViewById(R.id.time);
            holder.textUserName = (TextView) view.findViewById(R.id.userName);
            holder.textInterest = (TextView) view.findViewById(R.id.interest);
            holder.iamgePerson=(ImageView)view.findViewById(R.id.personality);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.imageLike.setImageResource(R.drawable.like);
        holder.imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                handler handler = new handler();
//                Message msg = new Message();
//                msg.what = 1;
//                handler.sendMessage(msg);
                if (TAG == false) {
                    TAG = !TAG;
                    holder.imageLike.setImageResource(R.drawable.like_change);
                } else {
                    TAG = !TAG;
                    holder.imageLike.setImageResource(R.drawable.like);
                }
            }
        });
        final int pos = i;
        holder.iamgePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler handler=new handler();
                Message msg=new Message();
                msg.what=2;
                msg.arg1 = pos;
                handler.sendMessage(msg);
            }
        });

        holder.textUserName.setText(mUserName.get(i));
        holder.textTime.setText(mTime.get(i));
        holder.textContent.setText(mContent.get(i));
        holder.textInterest.setText(mInterest.get(i));
        return view;
    }

    class ViewHolder {
        ImageView imageLike;
        ImageView iamgePerson;
        TextView textUserName;
        TextView textTime;
        TextView textContent;
        TextView textInterest;
    }


}
