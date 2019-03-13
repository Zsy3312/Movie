package com.example.a14532.mymovie.root.me;

import android.app.Application;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.MeModel;
import com.example.a14532.mymovie.data.local.GlobalData;

public class MePresenter implements MeContract.Presenter {
    private MeContract.View mMeView;
    private MeModel mMeModel;
    private GlobalData globalData;

    @Override
    public void start(){

    }

    public MePresenter(MeContract.View meView , MeModel meModel, Application data) {
        mMeView=meView;
        mMeModel=meModel;
        globalData=(GlobalData) data;

        meView.setPresenter(this);
    }

    @Override
    public void toLoginAct(){
        String username = globalData.getUserName();
        if(username.length()==0)
            mMeView.showLoginAct();
        else{
            mMeView.showMessage("当前登录账号为："+username);
        }
    }

    @Override
    public void toCollectAct(){
        String username = globalData.getUserName();
        if(username.length()==0){
            mMeView.showLoginAct();
        }
        else {
            mMeView.showCollectAct();
        }
    }
    @Override
    public void toHistoryAct(){
        String username = globalData.getUserName();
        if(username.length()==0){
            mMeView.showLoginAct();
        }
        else {
            mMeView.showHistoryAct();
        }
    }
    @Override
    public void toChangePhotoAct(){
        String username = globalData.getUserName();
        if(username.length()==0){
            mMeView.showLoginAct();
        }
        else {
            mMeView.showChangePhotoAct();
        }
    }

    @Override
    public void initUserInfo(){
        String username = globalData.getUserName();
        int id=Integer.parseInt(globalData.getUserPhoto());
        if(username.length()==0)
            username="未登录";
        mMeView.showUserInfo(username,id);
        if(username.equals("admin")){
            mMeView.showAdminAct();
        }
        else {
            mMeView.hideAdminAct();
        }
    }

    @Override
    public void quitLogin(){
        if(globalData.getUserName().length()==0)
            mMeView.showMessage("您还未登录");
        globalData.setUserName("");
        globalData.setUserPhoto(String.valueOf(R.drawable.guanyanren));
        initUserInfo();
    }

    @Override
    public void toUserCountAct(){
        mMeView.showUserCountAct();
    }

    @Override
    public void toMovieCountAct(){
        mMeView.showMovieCountAct();
    }
}
