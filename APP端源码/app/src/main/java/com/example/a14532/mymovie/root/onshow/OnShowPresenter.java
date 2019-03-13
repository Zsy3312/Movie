package com.example.a14532.mymovie.root.onshow;

import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;
import com.example.a14532.mymovie.data.model.OnShowModel;

public class OnShowPresenter implements OnShowContract.Presenter {
    private OnShowContract.View mOnShowView;
    private OnShowModel mOnShowModel;
    private int totalCount;
    private int start = 0;
    private int count = 10;
    private MovieBean movieData;
    private int loading=0;

    @Override
    public void start(){

    }

    public OnShowPresenter(OnShowContract.View onShowView , OnShowModel onShowModel) {
        mOnShowView=onShowView;
        mOnShowModel=onShowModel;

        onShowView.setPresenter(this);
    }

    @Override
    public void getOnShowMovie(){
        mOnShowModel.onShowRequest(new onDataResponseListener<MovieBean>() {
            @Override
            public void onSuccess(MovieBean dataBean) {
                if(start==0){//第一次
                    movieData =dataBean;
                    totalCount=dataBean.getTotal();
                    mOnShowView.setAdapter(movieData);
                    start=start+count;
                }
                else {
                    for(int i=0;i<dataBean.getSubjects().size();i++)
                    {
                        movieData.getSubjects().add(dataBean.getSubjects().get(i));
                    }
                    start=start+count;
                    mOnShowView.setLoadState(2);
                    loading =0;
                }
            }
            @Override public void onFailed(String string) {
                mOnShowView.showMessage(string);
                if(start!=0){
                    mOnShowView.setLoadState(2);
                    loading =0;
                }
            }
        },String.valueOf(start),String.valueOf(count));
    }

    @Override
    public void getMoreMovie(int size){
        if(loading ==0){//未在加载
            loading=1;
        }
        else if(loading ==1 ){//加载中
            return;
        }
        mOnShowView.setLoadState(1);

        if (size<totalCount) {
            getOnShowMovie();
        } else {
            // 显示加载到底的提示
            loading =0;
            mOnShowView.setLoadState(3);
        }
    }
}