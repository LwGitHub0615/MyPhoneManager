package com.example.administrator.myphonemanager.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myphonemanager.Application.DataApplication;
import com.example.administrator.myphonemanager.R;

/**
 * Created by Administrator on 2016/3/25.
 */
//   重写了控件RelativeLayout 的自定义控件 并设置了控件的点击监听
public class SettingItem extends RelativeLayout implements View.OnClickListener{

    //自定义控件属性的成员变量
    private TextView subhead;
    private TextView subheadContent;
    private CheckBox isChecked;

    //定义 Editor 去接收并提交信息到 App的轻量数据库SharedPreferences中,数据化保存
    private SharedPreferences.Editor editor;

    //自定义控件中 自定义属性的获取 设置
    String attrssubhead;
    String attrsonContent;
    String attrsoffContent;
    String attrsspKey;

    //自定义控件的初始化
    public SettingItem(Context context) {
        super(context);
        init(null);
    }

    //因为用到了不同的AttributeSet 所以 这就用到了   自定义属性的设置 传入
    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

        //自定义组合控件的初始化
      public void init(AttributeSet attrs) {

          editor = DataApplication.configSP.edit();
            //传入自定义组合控件的引用
          View inflate = View.inflate(getContext(), R.layout.item_settinglist, null);
             //找到自定义控件的 单个控件
          subhead = (TextView) inflate.findViewById(R.id.tv_setting_subhead);
          subheadContent = (TextView) inflate.findViewById(R.id.tv_setting_subheadContent);
          isChecked = (CheckBox) inflate.findViewById(R.id.cb_setting_isChecked);

//          subhead.setText("是否开启自动更新");   ？初始


          if (attrs != null) {  //null的情况

               //找到自定义组合控件的单个控件的 自定义属性的值
//     原先         attrssubhead = attrs.getAttributeValue("SettingItem", "subhead");
              attrssubhead = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "subhead");
              attrsonContent= attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "onContent");
              attrsoffContent = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "offContent");
              attrsspKey= attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "spKey");

          }
          subhead.setText(attrssubhead);

          //第一次进来 设置默认开启 自动更新 每次修改 保存在 DataApplication中 SharedPreferences 的使用 使用 怎么保存使用
          boolean autoUpdate = DataApplication.configSP.getBoolean(attrsspKey, true);
                                       //这里没改的话 另一个并不能获取 显示正确的信息  "autoUpdate"
          //系统SharedPreferences中存储的  设置显示在控件上
          if (autoUpdate) {
              subheadContent.setText(attrsonContent);
              isChecked.setChecked(true);

          } else {
              subheadContent.setText(attrsoffContent);
              isChecked.setChecked(false);

          }
               //添加显示 自定义控件
          addView(inflate);

          //没设置监听  没有效果   因为自身已经implements OnClickListener 自身开启监听即可
          setOnClickListener(this);
          //先是没有写出来重写的setOnClickListener  无效  现在 再来试试下 原先是Null
      }

    @Override          //需重写 传入监听对象 即本控件
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Override
    public void onClick(View v) {
        //这里获取SharedPreferences(同当前控件的显示状态
        boolean checked = isChecked.isChecked();
        //注意是点击事件的响应
        if(checked) subheadContent.setText(attrsoffContent);
        else subheadContent.setText(attrsonContent);
        isChecked.setChecked(!checked);
        editor.putBoolean(attrsspKey, !checked);
        editor.commit();
        /*   上同
         if (checked){
            isChecked.setChecked(false);
            subheadContent.setText(attrsoffContent);
            editor.putBoolean(attrsspKey, false);
            editor.commit();
        }
        else {
            isChecked.setChecked(true);
            subheadContent.setText(attrsonContent);
            editor.putBoolean(attrsspKey, true);
            editor.commit();
        }

         */

    }
}
