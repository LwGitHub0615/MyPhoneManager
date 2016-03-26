package com.example.administrator.myphonemanager.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/25.
 */

        //继承TextView 重写isFocused()方法 获取焦点  实现 layout文件 中设置 实现TextView的跑马灯效果
public class marqueeTextView extends TextView {

    public marqueeTextView(Context context) {
        super(context);
    }

    public marqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {  //设置 获取 焦点 实现跑马效果
        return true;  //super.isFocused();
    }
}
