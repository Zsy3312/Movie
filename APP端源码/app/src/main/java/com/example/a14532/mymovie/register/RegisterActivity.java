package com.example.a14532.mymovie.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.LoginModel;
import com.example.a14532.mymovie.utils.ActivityUtils;


public class RegisterActivity extends AppCompatActivity {

    RegisterPresenter mRegisterPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_act);

        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.registerContentFrame);

        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    registerFragment, R.id.registerContentFrame);
        }
        LoginModel registerModel=new LoginModel();

        // Create the presenter
        mRegisterPresenter = new RegisterPresenter(registerFragment,registerModel,getApplication());
    }
}