package com.example.administrator.myphonemanager;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.myphonemanager.Application.DataApplication;

public class SettingActivity extends ActionBarActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //隐藏
        final android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();

        //设置ActionBar的隐藏 和展示页的全屏

        //提交本地 和上传到GitHub 的区别  颜色的不同


/*

        sp=getSharedPreferences("config", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        final TextView tv_setting_text = (TextView) findViewById(R.id.tv_setting_text);
        final CheckBox cb_setting_update = (CheckBox) findViewById(R.id.cb_setting_update);

        //第一次进来 设置默认开启 自动更新 每次修改 保存在 DataApplication中 SharedPreferences 的使用 使用 怎么保存使用
        final boolean autoUpdate = DataApplication.configSP.getBoolean("autoUpdate", true);

        //        boolean checked = cb_setting_update.isChecked();

        if(autoUpdate){
            tv_setting_text.setText("开启自动更新");
            cb_setting_update.setChecked(true);

        }else {
            tv_setting_text.setText("关闭自动更新");
            cb_setting_update.setChecked(false);

        }


        cb_setting_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里获取的就是当前的状态
                boolean checked = cb_setting_update.isChecked();
                if (checked){
                    cb_setting_update.setChecked(true);
                    tv_setting_text.setText("自动更新开启");
                   editor.putBoolean("autoUpdate", true);
                    editor.commit();


                }
                else {

                    cb_setting_update.setChecked(false);
                    tv_setting_text.setText("自动更新关闭");
                   editor.putBoolean("autoUpdate",false);
                    editor.commit();

                }


            }
        });

*/


    }
}
