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

    // Day03 定义成员的editor提交数据到SharedPreferences 文件化保存
    static SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();

        //服务器的网址 资源的url路径 全局变量
        SERVER_PATH="";

        //configSP的初始化
        configSP= getSharedPreferences("config",MODE_PRIVATE);
        //editor的初始化
        editor =configSP.edit();
    }

    // Day03 创建 提交保存configSP的函数              （对应key value）
    public static void setConfigSPvalue(String spKey,String spValue){

         editor.putString(spKey,spValue);
        editor.commit();

    }



}
