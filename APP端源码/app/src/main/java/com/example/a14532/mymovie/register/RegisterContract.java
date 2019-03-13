package com.example.a14532.mymovie.register;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
//注册契约类
public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);//展示信息

        void back();//返回上一个界面

    }

    interface Presenter extends BasePresenter {
        void register(String username,String password,String passwordConfirm);//注册

        void setYear(String year);//设置用户年龄

        void setSex(String sex);//设置用户性别

        void setStudy(String study);//设置用户学历
    }
}