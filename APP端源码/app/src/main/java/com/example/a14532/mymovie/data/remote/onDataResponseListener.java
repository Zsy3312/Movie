package com.example.a14532.mymovie.data.remote;

//数据请求返回接口
public interface onDataResponseListener<T>{
    void onSuccess(T dataBean);
    void onFailed(String string);
}
