package com.example.administrator.myphonemanager.Application;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/3/25.
 */
//需要去声明 在manifest里 注意 注意！
public class DataApplication extends Application {

   static String SERVER_PATH;  //方便调用 全局常量存储

    //Day02的  开始共享数据  用做判断 自动更新的
    public static SharedPreferences configSP;
    @Override
    public void onCreate() {
        super.onCreate();

        //服务器的网址 资源的url路径 全局变量
        SERVER_PATH="";

        //configSP的初始化
        configSP= getSharedPreferences("config",MODE_PRIVATE);

    }
}
