package com.example.wuyufan.hiu.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import wu.seal.textwithimagedrawable.TextWithImageDrawable;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    String[] item = {"运 动", "约 炮", "搞 基", "学 习", "约 饭", "电 影", "博物馆", "瞎 逛", "讲 座", "实 习"};

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextWithImageDrawable textWithImageDrawable = new TextWithImageDrawable(mContext);
        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        textWithImageDrawable.setImagePosition(TextWithImageDrawable.Position.BOTTOM);
        //textWithImageDrawable.setDrawable(new ColorDrawable(Color.MAGENTA));
        //textWithImageDrawable.setTextSize(5);
        textWithImageDrawable.setText(item[position]);
        //textWithImageDrawable.setTextColor(Color.WHITE);
        imageView.setImageDrawable(textWithImageDrawable);
        imageView.setBackgroundColor(mThumbIds[position]);
        textWithImageDrawable.setImagePadding(5);

        /**
         * 设置此drawable的左边填充大小,单位px
         */
        textWithImageDrawable.setPaddingLeft(8);
        /**
         * 设置此drawable上方填充大小,单位px
         */
        textWithImageDrawable.setPaddingTop(6);
        /**
         * 设置此drawable的最大文字限制长度
         */
        textWithImageDrawable.setMaxTextLength(3);

        //imageView.setImageAlpha(30);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
//            Color.BLUE,Color.GREEN,Color.RED,Color.MAGENTA,Color.YELLOW,Color.LTGRAY,Color.BLUE,Color.GREEN,Color.RED,Color.MAGENTA
            Color.parseColor("#FFFFE0"), Color.parseColor("#F0FFF0"), Color.parseColor("#C1FFC1"), Color.parseColor("#B2DFEE"), Color.parseColor("#EEB4B4"),
            Color.parseColor("#FA8072"), Color.parseColor("#FF83FA"), Color.parseColor("#B03060"), Color.parseColor("#00FFFF"), Color.parseColor("#FFFFE0")
    };
}