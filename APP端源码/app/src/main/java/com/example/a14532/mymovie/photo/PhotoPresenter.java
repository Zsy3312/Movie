package com.example.a14532.mymovie.photo;

import android.app.Application;

import com.example.a14532.mymovie.R;

import com.example.a14532.mymovie.data.model.PhotoModel;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.example.a14532.mymovie.data.local.GlobalData;

import java.util.ArrayList;
import java.util.List;

public class PhotoPresenter implements PhotoContract.Presenter {
    private PhotoContract.View mPhotoView;
    private PhotoModel mPhotoModel;
    private GlobalData globalData;
    private List<Integer> photoList;

    @Override
    public void start(){

    }

    public PhotoPresenter(PhotoContract.View photoView , PhotoModel photoModel , Application data) {
        mPhotoView=photoView;
        mPhotoModel=photoModel;
        globalData=(GlobalData)data;

        mPhotoView.setPresenter(this);
    }

    @Override
    public void setPhoto(final String photoID) {
        String username = globalData.getUserName();

        System.out.println(photoID);
        mPhotoModel.updatePhotoRequest(new onDataResponseListener<ValueTextBean>() {
            @Override
            public void onSuccess(ValueTextBean dataBean) {
                globalData.setUserPhoto(photoID);
                mPhotoView.showMessage(dataBean.getText());
            }
            @Override public void onFailed(String string) {
                mPhotoView.showMessage(string);
            }
        },username,photoID);
    }

    @Override
    public void initView(){
        photoList = new ArrayList<>();
        photoList.add(R.drawable.kongbudianyingdianjujinghun);
        photoList.add(R.drawable.kongbudianyingdiyushizhe);
        photoList.add(R.drawable.kongbudianyingguiwahuihun);
        photoList.add(R.drawable.kongbudianyingguiwawahuazi);
        photoList.add(R.drawable.kongbudianyinghanniba);
        photoList.add(R.drawable.kongbudianyinghaoxingqiwumianjuren);
        photoList.add(R.drawable.kongbudianyingheimao);
        photoList.add(R.drawable.kongbudianyinghulijing);
        photoList.add(R.drawable.kongbudianyingjingshengjianjiao);
        photoList.add(R.drawable.kongbudianyingkulou);
        photoList.add(R.drawable.kongbudianyinglangren);
        photoList.add(R.drawable.kongbudianyingmunaiyi);
        photoList.add(R.drawable.kongbudianyingnvwu);
        photoList.add(R.drawable.kongbudianyingwaiguojiangshi);
        photoList.add(R.drawable.kongbudianyingwaixingren);
        photoList.add(R.drawable.kongbudianyingxiaochou);
        photoList.add(R.drawable.kongbudianyingxiaojiangshi);
        photoList.add(R.drawable.kongbudianyingxixiegui);
        photoList.add(R.drawable.kongbudianyingyixing);
        photoList.add(R.drawable.kongbudianyingzhenzi);

        mPhotoView.setAdapter(photoList);

        int id=Integer.parseInt(globalData.getUserPhoto());
        mPhotoView.showPhoto(id);
    }
}
