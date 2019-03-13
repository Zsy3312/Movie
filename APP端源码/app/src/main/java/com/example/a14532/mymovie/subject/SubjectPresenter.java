package com.example.a14532.mymovie.subject;

import android.app.Application;

import com.example.a14532.mymovie.data.model.SubjectModel;
import com.example.a14532.mymovie.data.remote.bean.SubjectInfoBean;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.example.a14532.mymovie.data.local.GlobalData;

import java.util.ArrayList;
import java.util.List;

public class SubjectPresenter implements SubjectContract.Presenter {

    private SubjectContract.View mSubjectView;
    private SubjectModel mSubjectModel;
    private GlobalData globalData ;
    private String mMovieID;
    private SubjectInfoBean movieBean;

    public SubjectPresenter(SubjectContract.View subjectView , SubjectModel subjectModel , String movieID, Application data) {
        mSubjectView=subjectView;
        mSubjectModel=subjectModel;
        mMovieID=movieID;
        globalData=(GlobalData)data;

        subjectView.setPresenter(this);
    }

    @Override
    public void start(){ }

    @Override
    public void getMovieSubjectInfo(){
        mSubjectModel.subjectRequest(new onDataResponseListener<SubjectInfoBean>() {
            @Override
            public void onSuccess(SubjectInfoBean dataBean) {
                String message = dataBean.getTitle();
                mSubjectView.showMessage(message);
                movieBean=dataBean;
                List<String> imageData=new ArrayList<>();
                int photoCount=dataBean.getPhotos().size();
                for(int i=0;i<photoCount;i++){
                    String photoImage = dataBean.getPhotos().get(i).getImage();
                    imageData.add(photoImage);
                }
                mSubjectView.initView(imageData,dataBean);
                addHistory();
            }
            @Override
            public void onFailed(String string) {
                mSubjectView.showMessage(string);
            }
        },mMovieID);
    }

    @Override
    public void collectMovie(){
        String username=globalData.getUserName();
        if(username.length()==0)
        {
            mSubjectView.showCollectButton();
            mSubjectView.showLoginAct();
            return;
        }
        String movieTitle =movieBean.getTitle();
        String movieYear = movieBean.getYear();
        String movieType = "";
        for(int i=0;i<3;i++) {
            if(i>=movieBean.getGenres().size())
                break;
            movieType=movieType + movieBean.getGenres().get(i) +"/";
        }
        String movieLanguage = "";
        if(movieBean.getLanguages().size()!=0)
            movieLanguage = movieBean.getLanguages().get(0);
        String movieDirector = "";
        if(movieBean.getDirectors().size()!=0)
            movieDirector = movieBean.getDirectors().get(0).getName();

        mSubjectModel.onCollectRequest(new onDataResponseListener<ValueTextBean>() {
            @Override
            public void onSuccess(ValueTextBean dataBean) {
                mSubjectView.showMessage(dataBean.getText());
                if(dataBean.getValue().equals("true")){//收藏成功
                    mSubjectView.showCancelCollectButton();
                }
                else{//收藏失败
                    mSubjectView.showCollectButton();
                }
            }
            @Override
            public void onFailed(String string) {
                mSubjectView.showMessage(string);
                mSubjectView.showCollectButton();
            }
        },mMovieID,movieTitle,username,movieYear,movieType,movieLanguage,movieDirector);
    }

    @Override
    public void cancelCollect(){
        String username=globalData.getUserName();
        if(username.length()==0)
        {
            mSubjectView.showLoginAct();
            return;
        }
        mSubjectModel.onCancelCollectRequest(new onDataResponseListener<ValueTextBean>() {
            @Override
            public void onSuccess(ValueTextBean dataBean) {
                mSubjectView.showMessage(dataBean.getText());
                if(dataBean.getValue().equals("true")){//取消收藏成功
                    mSubjectView.showCollectButton();
                }
                else{//取消收藏失败
                    mSubjectView.showCancelCollectButton();
                }
            }
            @Override
            public void onFailed(String string) {
                mSubjectView.showMessage(string);
                mSubjectView.showCancelCollectButton();
            }
        },mMovieID,username);
    }

    @Override
    public void getCollectOrNot(){
        String username=globalData.getUserName();
        if(username.length()==0) {
            mSubjectView.showCollectButton();
            return;
        }
        mSubjectModel.onCollectOrNotRequest(new onDataResponseListener<ValueTextBean>() {
            @Override
            public void onSuccess(ValueTextBean dataBean) {
                if(dataBean.getValue().equals("true")){//该电影已经收藏
                    mSubjectView.showCancelCollectButton();
                }
                else{//该电影未收藏
                    mSubjectView.showCollectButton();
                }
            }
            @Override
            public void onFailed(String string) {
                mSubjectView.showMessage(string);
            }
        },mMovieID,username);
    }

    @Override
    public void addHistory(){
        String username=globalData.getUserName();
        if(username.length()==0)//用户未登录不记录浏览记录
        {
            return;
        }
        String movieTitle=movieBean.getTitle();
        mSubjectModel.onAddHistoryRequest(new onDataResponseListener<ValueTextBean>() {
            @Override
            public void onSuccess(ValueTextBean dataBean) {//浏览记录登记成功与否不显示
            }
            @Override
            public void onFailed(String string) {
            }
        },mMovieID,movieTitle,username);
    }

    @Override
    public void deleteHistory(){
        String username=globalData.getUserName();
        if(username.length()==0)
        {
            mSubjectView.showLoginAct();
            return;
        }
        mSubjectModel.onDeleteHistoryReuest(new onDataResponseListener<ValueTextBean>() {
            @Override
            public void onSuccess(ValueTextBean dataBean) {
                mSubjectView.showMessage(dataBean.getText());
            }
            @Override
            public void onFailed(String string) {
                mSubjectView.showMessage(string);
            }
        },mMovieID,username);
    }
}
