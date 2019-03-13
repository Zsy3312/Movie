package com.example.a14532.mymovie.register;

import android.app.Application;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.LoginModel;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.example.a14532.mymovie.data.local.GlobalData;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter implements RegisterContract.Presenter{
    private RegisterContract.View mRegisterView;
    private LoginModel mRegisterModel;
    private GlobalData globalData ;

    private String yearString;
    private String sexString;
    private String studyString;

    @Override
    public void start(){

    }

    @Override
    public void register(String username, String password,String passwordConfirm){
        if(username.length()==0) {
            mRegisterView.showMessage("请输入用户名");
        }
        else if(username.length()>15){
            mRegisterView.showMessage("用户名最长为十五个字符");
        }
        else if(password.length()==0) {
            mRegisterView.showMessage("请输入密码");
        }
        else if(!password.equals(passwordConfirm)){
            mRegisterView.showMessage("两次输入的密码不同");
        }
        else{
            String photo= String.valueOf(R.drawable.guanyanren);
            Map<String,String> map = new HashMap<>();
            map.put("username",username);
            map.put("password",password);
            map.put("photo",photo);
            map.put("year",yearString);
            map.put("sex",sexString);
            map.put("study",studyString);

            mRegisterModel.onRegisterRequest(new onDataResponseListener<ValueTextBean>() {
                @Override
                public void onSuccess(ValueTextBean dataBean) {
                    mRegisterView.showMessage(dataBean.getText());
                    if(dataBean.getValue().equals("true")){//注册成功
                        mRegisterView.back();
                    }
                    else{//注册失败

                    }
                }
                @Override public void onFailed(String string) {
                    mRegisterView.showMessage(string);
                }
            },map);
        }
    }

    public RegisterPresenter(RegisterContract.View registerView , LoginModel registerModel, Application data) {
        mRegisterView=registerView;
        mRegisterModel=registerModel;

        registerView.setPresenter(this);
        globalData =(GlobalData) data;
    }

    public void setYear(String year){
        yearString=year;
    }

    public void setSex(String sex){
        sexString=sex;
    }

    public void setStudy(String study){
        studyString=study;
    }
}