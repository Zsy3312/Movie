package com.example.a14532.mymovie.data.model;

import com.example.a14532.mymovie.data.remote.RetrofitClient;
import com.example.a14532.mymovie.data.remote.UserApi;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.a14532.mymovie.R;
//登录的model
public class LoginModel  {
    private Call<ValueTextBean> call;

    //登入请求
    public void onLoginRequest(final onDataResponseListener onDataResponseListener, String username, String password) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);

        call = request.getLoginCall(map);
        call.enqueue(new Callback<ValueTextBean>() {
            @Override
            public void onResponse(Call<ValueTextBean> call, Response<ValueTextBean> response) {
                ValueTextBean valueTextBean = response.body();
                if (valueTextBean != null){
                    onDataResponseListener.onSuccess(valueTextBean);
                }
            }
            @Override
            public void onFailure(Call<ValueTextBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    //注册请求
    public void onRegisterRequest(final onDataResponseListener onDataResponseListener,Map<String,String> map) {
        UserApi request = new RetrofitClient().getUserService();

        call = request.getRegisterCall(map);
        call.enqueue(new Callback<ValueTextBean>() {
            @Override
            public void onResponse(Call<ValueTextBean> call, Response<ValueTextBean> response) {
                ValueTextBean valueTextBean = response.body();
                if (valueTextBean != null){
                    onDataResponseListener.onSuccess(valueTextBean);
                }
            }
            @Override
            public void onFailure(Call<ValueTextBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }

    public void cancelRequest(){
        call.cancel();
    }
}

