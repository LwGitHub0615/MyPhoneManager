package com.example.administrator.myphonemanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myphonemanager.Application.DataApplication;
import com.example.administrator.myphonemanager.Utils.MD5digest;

//注释 快给我注释 快快的
public class HomeActivity extends ActionBarActivity {

    private GridView gv_home_item;
    private TextView tv_home_mtext;

    //设置为9个宫格 目录式
    private final int CONTENT_NUM = 9;

    //与Gridlayout类似于  123,456,789
    private int[] iconarray ={R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app
            ,    R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
            R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};

    private  String[] titles={"手机防盗","通讯卫士","软件管理",
            "进程管理","流量统计","手机杀毒",
            "缓存清理","高级工具","设置中心"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //第二部分 跑马灯效果的 设置 法1：selected 如下 法2：onfocus 继承于TextView 重写的isFocused()

        tv_home_mtext = (TextView) findViewById(R.id.tv_home_mtext);

        tv_home_mtext.setText("每天我都在这里等你 >*。*< Hah 更新于2016年3月25日晚上九点03分");
        tv_home_mtext.setSelected(true);

        //法2：onfocus
            /*
             View / marqueeTextView 的实现
             在activity_home中引用即可
                    */

        //第三部分  关键问题在于 自定义(组合)控件 九宫格的问题
        gv_home_item = (GridView) findViewById(R.id.gv_home_item);
           //GridView BaseAdapter的重写 使自身View显示的自定义化
          gv_home_item.setAdapter(new nineAdapter());
           //设定点击事件的相应响应事件
        gv_home_item.setOnItemClickListener(new nineItemListener());


    }

    //这一个是点击的事件响应  响应九宫格item的点击事件

    class nineItemListener implements AdapterView.OnItemClickListener{

        //点击那个Item 响应相对应的 Activity
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position){

                case 0:
                    //查看是否开启 及设置密码 （初始化设置密码 开启手机防盗/进入手机防盗
                    String phonesafe_pwd=DataApplication.configSP.getString("phonesafe_pwd","");

                    if(phonesafe_pwd.isEmpty()){  //.isEmpty() 判断两种情况 null 没有内存地址 "" 的情况 也就是没有内容的情况
                        showSetpwdDialog();

                    }else {
                        showInputpwdDialog();

                    }

                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                    break;
                default:
                    break;
            }


        }


    }

    //未设置手机防盗开启 设置密码 及 开启手机防盗功能
    private void showSetpwdDialog() {
        //获取AlertDialog 的Builder对象  便于显示设置密码页面
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //HomeActivity.
         //找到自定义控件的View 对象
        View setinflate = View.inflate(this, R.layout.dialog_setpwd, null);

        //Dialog内部使用final      找到自定义组合控件中的特定控件
        final EditText dialogsetpwd_pwd1= (EditText) setinflate.findViewById(R.id.ed_dialogsetpwd_pwd1);
        final EditText dialogsetpwd_pwd2= (EditText) setinflate.findViewById(R.id.ed_dialogsetpwd_pwd2);

         Button dialogsetpwd_cancel= (Button) setinflate.findViewById(R.id.bt_dialogsetpwd_cancel);
        Button dialogsetpwd_confirm= (Button) setinflate.findViewById(R.id.bt_dialogsetpwd_confirm);


/*
         //为什么忽然换成下方
        builder.setView(setinflate);
        builder.show();
*/
          //转换为这样 以便于 点击取消事件的响应
        builder.setView(setinflate);
         final AlertDialog dialog = builder.create();
        dialog.show();
        //取消事件的响应
        dialogsetpwd_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();

            }
        });
        //确认 按钮的响应监听
        dialogsetpwd_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd1 = dialogsetpwd_pwd1.getText().toString();
                String pwd2 = dialogsetpwd_pwd2.getText().toString();
                 //取出用户录入数据的值 用作判断
                if(!pwd1.isEmpty()&!pwd2.isEmpty()){//TextUtils.isEmpty(pwd1)
                     /*  knowledge point   此处 试图 一个为空就报不能为空
                     &对每一个都判断；
                      &&只要前面是false就输出false，而不继续判断后面了
                      */

                    if(pwd1.equals(pwd2)){

                        //提出了保存敏感数据的保存问题 使用MD5 加密算法 计算加密过后的字符串再存储
                        String pwd =MD5digest.getDM5string(pwd1);
                          //suoyi,       下方是成员数据库中定义的一个保存SharedPreferences函数
                        DataApplication.setConfigSPvalue("phonesafe_pwd",pwd);
                        startActivity(new Intent(HomeActivity.this, PhoneSafeActivity.class));
                        dialog.dismiss();
                    }else {           //貌似是有优势的 自动获取 手动输入 heh
                        Toast.makeText(getApplicationContext(),"两次输入不一致",Toast.LENGTH_SHORT).show();

                    }

                }else {

                    Toast.makeText(HomeActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    //手机防盗开启 输入密码进入手机防盗界面
    private void showInputpwdDialog() {
          //获取Dialog的对象 用于填充自定义组合控件
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
          //自定义组合控件的调用
        View inputinflate = View.inflate(this, R.layout.dialog_inputpwd, null);

        final EditText dialoginputpwd_pwd= (EditText) inputinflate.findViewById(R.id.ed_dialoginputpwd_pwd);
        Button dialoginput_cancel = (Button) inputinflate.findViewById(R.id.bt_dialoginput_cancel);
        Button dialoginput_confirm = (Button) inputinflate.findViewById(R.id.bt_dialoginput_confirm);

/*
        //这次知道了吗
        builder.setView(inputinflate);
        builder.show();
*/

//        builder.setView(inputinflate);
        //调用Dialog的引用 显示自定义组合控件
        final AlertDialog dialog = builder.create();
        dialog.setView(inputinflate,0,0,0,0); //与builder的一样 只是说兼容了低版本
        dialog.show();  //的显示
            //监听自定义组合控件的 cancel的事件
        dialoginput_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
            //监听 confirm
        dialoginput_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pwd = dialoginputpwd_pwd.getText().toString();
                 //取出输入数据 判断
                if (!pwd.isEmpty()){

                    //开始使用MD5加密保存 所以 此处也须加密后 再与数据库中的存储数据进行对比
                    String pwddigest =  MD5digest.getDM5string(pwd);

                    String phonesafe_pwd=   DataApplication.configSP.getString("phonesafe_pwd", "");

                    //savepwd 还是可能为空的。时间差
                    if (!phonesafe_pwd.isEmpty()){   //优势
                        if(phonesafe_pwd.equals(pwddigest)){   //pwd
                            dialog.dismiss();
                            startActivity(new Intent(HomeActivity.this,PhoneSafeActivity.class));
                        }
                        else
                            Toast.makeText(HomeActivity.this, "输入密码有误，请重输！", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(HomeActivity.this, "输入密码不能为空！", Toast.LENGTH_SHORT).show();

            }
        });


    }
/*
    //是可以设置响应事件的 但是不算合适 所，
    private void dialoginput_Cancel() {

    }
    private void dialoginput_Confirm() {

    }
*/

    //出来注释  什么的到底是：设置GirdView的显示,内容,,
    class nineAdapter extends BaseAdapter{


        //设置9宫格的   设置GirdView的宫格 数量
        @Override
        public int getCount() {
            return CONTENT_NUM;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // GridView 优化  （资源浪费的和 滑动响应
            // 法1：复用convertView   法2：viewHolder
                           //自定义控件的调用
            View view=View.inflate(HomeActivity.this,R.layout.item_gridview,null);
                             //找到自定义控件的指定组件
          ImageView gridview_icon= (ImageView) view.findViewById(R.id.iv_home_gridview_icon);
          TextView gridview_title= (TextView) view.findViewById(R.id.tv_home_gridview_title);

            // 向相对应的自定义控件中的组件中填充 内容
            gridview_icon.setImageResource(iconarray[position]);
            gridview_title.setText(titles[position]);

            //返回自定义控件的调用
            return view;
        }
    }

}
