package com.example.a14532.mymovie.root.me;

import com.example.a14532.mymovie.BasePresenter;
import com.example.a14532.mymovie.BaseView;
//我的Tab契约类
public interface MeContract {
    interface View extends BaseView<MeContract.Presenter> {
        void showMessage(String message);//展示信息

        void showLoginAct();//展示登录界面

        void showCollectAct();//展示收藏列表界面

        void showHistoryAct();//展示历史记录列表界面

        void showChangePhotoAct();//展示更换头像界面

        void showUserInfo(String name,int id);//展示用户信息

        void showAdminAct();//展示管理员操作

        void hideAdminAct();//隐藏管理员操作

        void showUserCountAct();//展示用户统计界面

        void showMovieCountAct();//展示电影统计界面
    }

    interface Presenter extends BasePresenter {
        void toLoginAct();//前往登录界面

        void toCollectAct();//前往收藏列表界面

        void toHistoryAct();//前往历史记录列表界面

        void toChangePhotoAct();//前往更换头像界面

        void toUserCountAct();//前往用户统计界面

        void toMovieCountAct();//前往电影数据统计界面

        void initUserInfo();//初始化用户信息

        void quitLogin();//退出登录
    }
}
