package com.example.a14532.mymovie.data.model;

import com.example.a14532.mymovie.data.remote.RetrofitClient;
import com.example.a14532.mymovie.data.remote.UserApi;
import com.example.a14532.mymovie.data.remote.bean.UserCountBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//用户数量统计model
public class UserCountModel {
    private Call<UserCountBean> userCountCall;

    public void userCountRequest(final onDataResponseListener onDataResponseListener) {
        UserApi request = new RetrofitClient().getUserService();

        userCountCall = request.getUserCountCall();
        userCountCall.enqueue(new Callback<UserCountBean>() {
            @Override
            public void onResponse(Call<UserCountBean> call, Response<UserCountBean> response) {
                UserCountBean userCountBean = response.body();
                if (userCountBean != null) {
                    onDataResponseListener.onSuccess(userCountBean);
                }
            }
            @Override
            public void onFailure(Call<UserCountBean> call, Throwable t) {
                onDataResponseListener.onFailed(t.getMessage());
            }
        });
    }
}
