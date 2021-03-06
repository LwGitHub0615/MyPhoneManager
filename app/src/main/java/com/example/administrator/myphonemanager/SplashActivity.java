package com.example.administrator.myphonemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myphonemanager.Application.DataApplication;
import com.example.administrator.myphonemanager.Utils.HttpUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends ActionBarActivity {

    //已安装app的版本
    private String currentversionName;

    //定义 更新的 这个参数什么的
    private static final int MSG_isNewest=0;
    private static final int MSG_canUpdate=1;

    // 考虑到使用问题 就把提成成员变量
    String path ="http://192.168.3.50/MyPhoneManager/";
    private ProgressBar pb_splash_percent;

    //姑且越新越上(函数   定义成员变量越新越下
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //获取当前app的版本
        currentversionName = getVersionName();

        //给设置文字
        TextView tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本:"+currentversionName); // hah

        //显示 ProcessBar
        pb_splash_percent = (ProgressBar) findViewById(R.id.pb_splash_percent);

        //获取的当前app的服务端版本 信息

        if(DataApplication.configSP.getBoolean("autoUpdate",true))
        getNewVersion();
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //设置在没开启自动更新的情况下 等待3秒 给展示页面再转向HomeActivity
                    TurntoHome();
                }
            }).start();
        }

    }

    //Cancel取消以后

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //用户取消更新操作的时候
        if(requestCode==RESULT_CANCELED){

            //进入app主页面 HomeActivity
            TurntoHome();

        }

    }

    //终于要去主页面了 是不是有点小激动了呢 但是 还是有那么多问题在
    private void TurntoHome() {

        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
        finish();

    }

    //Handler之间的消息传递机制
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case MSG_canUpdate:
                    //获取 当前版本 和 服务器最新版本 判断是否需要更新的提示

                    //注意获取的是Float 而不是int 所以

                    String[] mes = (String[]) msg.obj;

                    updateVersion(mes);
                    break;
                case MSG_isNewest:
                    //获取 当前版本 和 服务器最新版本 判断已经是服务器端最新版本

                    break;


            }


        }
    };


    //安装应用的函数

    void install(File file){

//调用系统的应用安装应用

        //此乃标准步骤 所以 尔等，，
        Intent intent =new Intent();

        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

        startActivity(intent);

    }

    // 出来注释   使用AsyncHttpClient 连接服务器端的 结果处理
    class myResponseHandler extends AsyncHttpResponseHandler{

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {


/*       有关路径的获取问题

//  获取当前手机的SD卡的路径
             Environment.getExternalStorageDirectory().getAbsolutePath()

//  获取当前程序路径
            getApplicationContext().getFilesDir().getAbsolutePath();

//  获取该程序的安装包路径
            String path=getApplicationContext().getPackageResourcePath();

//  获取程序默认数据库路径
            getApplicationContext().getDatabasePath(s).getAbsolutePath();

*/

            //成功的情况下 获取 文件 保存在应用目录下 ？会不会在安装加载中出问题
          String  filepath = getApplicationContext().getCacheDir().getAbsolutePath();
            File apkfile = new File(filepath+"/install.apk");
            try {
                FileOutputStream fos=new FileOutputStream(apkfile);
                fos.write(bytes);
                fos.close();

                //Day02优化 添加下载进度提示



                //安装应用的操作函数
                install(apkfile);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

         //Day02 下载进度的显示
        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

            //显示进度
                pb_splash_percent.setMax((int) totalSize);

             pb_splash_percent.setProgress((int) bytesWritten);

        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            //失败的话 抬出一个提示框
            Toast.makeText(SplashActivity.this,"下载失败",Toast.LENGTH_SHORT).show();

            TurntoHome();
        }
    }



    //提示 用户新版本 询问是否更新

    //version 中版本  --> 版本 description 下载的相对路径

    private void updateVersion(final String[] serverVersionmes) {

        new AlertDialog.Builder(this)
                .setTitle("发现新版本")
                .setMessage(serverVersionmes[0]+"版本："+serverVersionmes[1])
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //用户确认更新的操作
                        // 使用 AsyncHttpClient 解析  获取apk文件  安装

                        //android-async-http-1.4.8.jar 最新版的解析jar包 添加到app的libs中 调用
                        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
                        //为了此处方便使用 使成为final 匿名内部类调用参数
                        asyncHttpClient.get(path+serverVersionmes[2],new myResponseHandler());

                        //Day02 进度条显示
                        pb_splash_percent.setVisibility(View.VISIBLE);

                        //关键在与 怎么样 获取文件  及开启安装

                        //myResponseHandler 中 onSuccess情况下 去获取安装文件 及其安装
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //用户取消更新 的操作 进入App主页面操作
                        TurntoHome();
                    }
                })
                .show();


    }


    //获取当前app的版本
    public String getVersionName(){

        String versionName="";
//          管理当前手机应用的软件包名
        PackageManager packageManager = getPackageManager();

        try {
            //当前软件的包信息
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            //获取到版本信息
            int versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//       是否需要返回空字符串   ？
        return versionName;
    }

    /*服务器端的建立
      version.json  设置获取版本型号 解析Json文件
      apk的服务器的位置

     */

    //获取服务端的 app版本信息 判断 是否有更新
    public void getNewVersion(){

        //主线程不允许做耗时操作
        new Thread(){
            @Override
            public void run() {
                super.run();
                //获取的本地服务端
                String versionpath=path+"/version.json";

                try {
                    URL url=new URL(versionpath);
//                  获得连接对象
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setConnectTimeout(10000);
                    con.setReadTimeout(10000);

                    con.connect();
                    int responseCode = con.getResponseCode();

                    //正常的情况  但是？ 网络问题 服务器问题

                    //所以 根据条件去设置mes.what .object  finally中 设置信息传递

//               catch中 关于错误的捕获 各种异常在 what 中的 错误码设置  有便于获取错误信息 debug
                    if(responseCode==200) {

                        InputStream inputStream = con.getInputStream();

                        String newversionName = HttpUtils.getTextFromStream(inputStream);
//                       显示服务器端获取的Version版本信息
                        Log.i("getNewVersion", newversionName);
                        //忽然发现 没加网络权限  heh哈
                        //先换个图标试下咯
                        //没打印出来信息 为什么捏 onCreate没有调用啊啊啊

//                       利用Json解析获取 字符串   获取JSON对象
                        JSONObject jsonObject = new JSONObject(newversionName);

                        //分步获取参数信息
                        String version = jsonObject.getString("version");
                        String description = jsonObject.getString("description");
                        String download_url = jsonObject.getString("Download_url");

                        //并入数组 利用Handler 的消息传递
                        String[] mes = {version, description, download_url};

                        //此处利用获取的两个版本的对比 判断 是否更新 或提示更新的操作之类的
                        float serverVersion = Float.parseFloat(version);
                        float currentVersion = Float.parseFloat(currentversionName);

                        if (serverVersion>currentVersion) {
                            Message message = handler.obtainMessage();

                            message.what = MSG_canUpdate;
                            message.obj = mes;
                            //创建Handler传递消息
                            handler.sendMessage(message);
                        }else {

                            //然而并不显示  我也是，，
                            Toast.makeText(SplashActivity.this,"更新操作失败,即将进入主页面，，，",Toast.LENGTH_LONG).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    //设置在没开启自动更新的情况下 等待3秒 给展示页面再转向HomeActivity
                                    TurntoHome();
                                }
                            }).start();

                        }

                    }else {
                        TurntoHome();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    TurntoHome();
                } catch (IOException e) {
                    e.printStackTrace();
                    TurntoHome();
                } catch (JSONException e) {
                    e.printStackTrace();
                    TurntoHome();
                }

            }
        }.start();




    }

}
