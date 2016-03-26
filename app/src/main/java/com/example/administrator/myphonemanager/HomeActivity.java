package com.example.administrator.myphonemanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myphonemanager.Application.DataApplication;

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
