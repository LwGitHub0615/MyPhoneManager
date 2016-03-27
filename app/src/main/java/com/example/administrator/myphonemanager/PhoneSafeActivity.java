package com.example.administrator.myphonemanager;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class PhoneSafeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_safe);

        //SharedPreferences没有开启保存的密码及设置的情况下 设置密码之类 或者 输入密码才可以进去
//        ActionBar 工具栏 操作杆        preferences   参数选择（preference的复数）；选择权
//salt  用盐腌；给…加盐；将盐撒在道路上使冰或雪融化  n'盐；风趣，刺激性
//        crack 使破裂；打开；变声 破解
    }
}
