package com.example.a14532.mymovie.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.LoginModel;
import com.example.a14532.mymovie.utils.ActivityUtils;
//登录Activity
public class LoginActivity extends AppCompatActivity {

    LoginPresenter mLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.loginContentFrame);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.loginContentFrame);
        }
        LoginModel loginModel=new LoginModel();

        // Create the presenter
        mLoginPresenter = new LoginPresenter(loginFragment,loginModel,getApplication());
    }
}
