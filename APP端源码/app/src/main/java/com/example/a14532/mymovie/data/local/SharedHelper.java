package com.example.a14532.mymovie.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a14532.mymovie.R;

import java.util.HashMap;
import java.util.Map;
//抽象出来的本地SharedPrefrence操作类
public class SharedHelper {

    private Context mContext;

    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }


    //定义一个保存数据的方法
    public void saveUsername(String username) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.commit();
    }

    public void savePhoto(String photo) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("photo", photo);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("photo", sp.getString("photo", String.valueOf(R.drawable.guanyanren)));
        return data;
    }
}