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
//更换头像界面的model
public class PhotoModel {
    private Call<ValueTextBean> call;

    public void updatePhotoRequest(final onDataResponseListener onDataResponseListener, String username, String photo) {
        UserApi request = new RetrofitClient().getUserService();

        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("photo",photo);

        call = request.updatePhotoCall(map);
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
}
