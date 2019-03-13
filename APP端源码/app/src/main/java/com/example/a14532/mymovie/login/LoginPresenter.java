package com.example.a14532.mymovie.login;

import android.app.Application;

import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.model.LoginModel;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.example.a14532.mymovie.data.local.GlobalData;

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View mLoginView;
    private LoginModel mLoginModel;
    private GlobalData globalData ;

    @Override
    public void start(){

    }

    @Override
    public void login(String username, String password) {
        final String name = username;
        if(username.length()==0) {
            mLoginView.showMessage("请输入用户名");
        }
        else if(password.length()==0) {
            mLoginView.showMessage("请输入密码");
        }
        else{
            mLoginModel.onLoginRequest(new onDataResponseListener<ValueTextBean>() {
                @Override
                public void onSuccess(ValueTextBean dataBean) {
                    if(dataBean.getValue().equals("true")){//登入成功
                        mLoginView.showMessage("登录成功");
                        globalData.setUserPhoto(dataBean.getText());
                        globalData.setUserName(name);
                        mLoginView.back();
                    }
                    else{//登入失败
                        mLoginView.showMessage(dataBean.getText());
                    }
                }
                @Override public void onFailed(String string) {
                    mLoginView.showMessage(string);
                }
            },username,password);
        }
    }

    public void toRegisterAct(){
        mLoginView.showRegisterAct();
    }

    public LoginPresenter(LoginContract.View loginView , LoginModel loginmodel, Application data) {
        mLoginView=loginView;
        mLoginModel=loginmodel;

        loginView.setPresenter(this);
        globalData =(GlobalData) data;
    }
}
