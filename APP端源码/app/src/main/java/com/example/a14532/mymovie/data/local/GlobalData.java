package com.example.a14532.mymovie.data.local;

import android.app.Application;

import java.util.Map;
//全局变量类
public class GlobalData extends Application {
    private SharedHelper sh;

    private String userName;

    private String userPhoto;

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String name){
        this.userName=name;
        sh.saveUsername(name);
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
        sh.savePhoto(userPhoto);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        initUserInfo();
    }

    public void initUserInfo(){
        sh = new SharedHelper(getApplicationContext());
        Map<String,String> data = sh.read();
        userName=data.get("username");
        userPhoto=data.get("photo");
    }
}
