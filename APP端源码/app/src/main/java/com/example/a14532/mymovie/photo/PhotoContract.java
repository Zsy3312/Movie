package com.example.a14532.mymovie.photo;


import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;

import java.util.List;
//头像更换契约类
public interface PhotoContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);//信息展示

        void setAdapter(List<Integer> photoList);//设置头像网格Adapter

        void showPhoto(int id);//设置头像
    }

    interface Presenter extends BasePresenter{
        void setPhoto(String photoId);//设置头像

        void initView();//初始化界面
    }
}
