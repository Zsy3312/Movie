package com.example.a14532.mymovie.photo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.data.model.PhotoModel;
import com.example.a14532.mymovie.utils.ActivityUtils;


public class PhotoActivity extends AppCompatActivity {
    PhotoPresenter mTypeSearchPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.a14532.mymovie.R.layout.photo_act);

        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager()
                .findFragmentById(com.example.a14532.mymovie.R.id.photoContentFrame);

        if (photoFragment == null) {
            photoFragment = PhotoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    photoFragment, com.example.a14532.mymovie.R.id.photoContentFrame);
        }
        PhotoModel typeSearchModel=new PhotoModel();

        // Create the presenter
        mTypeSearchPresenter = new PhotoPresenter(photoFragment,typeSearchModel,getApplication());
    }
}