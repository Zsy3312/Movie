package com.example.a14532.mymovie.login;
import com.example.a14532.mymovie.BaseView;
import com.example.a14532.mymovie.BasePresenter;
//登录Contract
public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);//展示信息

        void back();//返回上一个界面

        void showRegisterAct();//展示注册界面
    }

    interface Presenter extends BasePresenter{
        void login(String username,String password);//登录

        void toRegisterAct();//跳转到注册界面
    }
}
