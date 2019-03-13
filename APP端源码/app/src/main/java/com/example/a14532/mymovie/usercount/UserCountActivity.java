package com.example.a14532.mymovie.usercount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.UserCountModel;
import com.example.a14532.mymovie.utils.ActivityUtils;

public class UserCountActivity extends AppCompatActivity {

    UserCountPresenter mUserCountPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_user_act);

        UserCountFragment userCountFragment = (UserCountFragment) getSupportFragmentManager()
                .findFragmentById(R.id.userCountContentFrame);

        if (userCountFragment == null) {
            userCountFragment = UserCountFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    userCountFragment, R.id.userCountContentFrame);
        }
        UserCountModel userCountModel=new UserCountModel();

        // Create the presenter
        mUserCountPresenter = new UserCountPresenter(userCountFragment,userCountModel);
    }
}