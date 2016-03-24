package com.example.administrator.myphonemanager.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/3/24.
 */
public class HttpUtils {

   public static String getTextFromStream(InputStream inputStream){

        String versionName="";

        ByteArrayOutputStream baos=new ByteArrayOutputStream();

        byte[] bytes=new byte[1024*10];

        int length=-1;

        try {
            while((length=inputStream.read(bytes))!=-1){

                baos.write(bytes,0,length);
            }

            baos.close();

            //GBK的区别          utf-8 浏览器没乱码 但是 android显示乱码
            versionName=baos.toString("gbk");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return versionName;
    }









}
