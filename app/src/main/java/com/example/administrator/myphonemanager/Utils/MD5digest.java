package com.example.administrator.myphonemanager.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/3/26.
 */
public class MD5digest {

    //此函数通过系统的MD5 算法加密 password 返回一个字符串
    public static String getDM5string(String password){

        String afterpwd="";
         //可变长字符串
        StringBuffer stringBuffer =new StringBuffer();
        try {
            //获取MD5 算法摘要
            MessageDigest mes=MessageDigest.getInstance("MD5");
            //获取字节数组
            byte[] digest = mes.digest(password.getBytes());

            for (byte b : digest) {
                 //此处&运算的问题？_？!
                int ret=b&0xFF;

                String hexString = Integer.toHexString(ret);
                System.out.println("先打印："+hexString);
                if(hexString.length()==1){
                    stringBuffer.append("0");
                }
                stringBuffer.append(hexString);

            }
            System.out.println("最后："+stringBuffer);


        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         //加密后的字符串
        afterpwd=stringBuffer.toString();

        return afterpwd;

    }

}
