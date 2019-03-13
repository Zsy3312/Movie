package com.example.a14532.mymovie.subject;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
import com.example.a14532.mymovie.data.remote.bean.SubjectInfoBean;

import java.util.List;
//电影详情契约类
public interface SubjectContract {
    interface View extends BaseView<Presenter> {
        void showMessage(String message);//展示信息

        void initView(List<String> networkImages,SubjectInfoBean dataBean);//初始化界面

        void showCollectButton();//显示收藏按钮

        void showCancelCollectButton();//显示取消收藏按钮

        void showLoginAct();//展示登录界面
    }

    interface Presenter extends BasePresenter {
        void getMovieSubjectInfo();//获取电影详情数据

        void collectMovie();//收藏电影

        void cancelCollect();//取消收藏电影

        void getCollectOrNot();//判断电影收藏与否

        void addHistory();//增加浏览记录

        void deleteHistory();//删除浏览记录
    }
}
