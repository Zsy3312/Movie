package com.example.a14532.mymovie.typesearch;

import com.example.a14532.mymovie.data.model.MovieListModel;
import com.example.a14532.mymovie.data.model.TypeSearchModel;
import com.example.a14532.mymovie.data.remote.MovieApi;
import com.example.a14532.mymovie.data.remote.RetrofitClient;
import com.example.a14532.mymovie.data.remote.UserApi;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.data.remote.bean.SubjectInfoBean;
import com.example.a14532.mymovie.data.remote.bean.ValueTextBean;
import com.example.a14532.mymovie.data.remote.onDataResponseListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class TypeSearchPresenter implements TypeSearchContract.Presenter {
    private TypeSearchContract.View mTypeSearchView;
    private MovieListModel mTypeSearchModel;

    private int totalCount;
    private int start = 0;
    private int count = 18;
    private MovieBean movieData;
    private int loading=0;
    private String now_type="";
    @Override
    public void start(){

    }

    public TypeSearchPresenter(TypeSearchContract.View typeSearchView , MovieListModel typeSearchModel) {
        mTypeSearchView=typeSearchView;
        mTypeSearchModel=typeSearchModel;

        mTypeSearchView.setPresenter(this);
    }

    @Override
    public void getTypeMovie(String type) {
        if(type.equals(now_type)==false) {//新的类型
            start=0;
            now_type=type;
        }
        String path="search";
        Map<String,String> map = new HashMap<>();
        map.put("tag",type);
        map.put("apikey","0b2bdeda43b5688921839c8ecb20399b");
        map.put("start",String.valueOf(start));
        map.put("count",String.valueOf(count));
        map.put("client","message");//无用参数
        map.put("udid","message");//无用参数
        mTypeSearchModel.movieListRequest(new onDataResponseListener<MovieBean>() {
            public void onSuccess(MovieBean dataBean) {
                if(start==0){//第一次
                    movieData =dataBean;
                    totalCount=dataBean.getTotal();
                    mTypeSearchView.setAdapter(movieData);
                    start=start+count;
                }
                else {
                    for(int i=0;i<dataBean.getSubjects().size();i++)
                    {
                        movieData.getSubjects().add(dataBean.getSubjects().get(i));
                    }
                    start=start+count;
                    mTypeSearchView.setLoadState(2);
                    loading =0;
                }
            }
            @Override public void onFailed(String string) {
                mTypeSearchView.showMessage(string);
                mTypeSearchView.setLoadState(2);
                loading =0;
            }

        },path,map);
    }

    @Override
    public void getMoreMovie(int size,String type){
        if(loading ==0){//未在加载
            loading=1;
        }
        else if(loading ==1 ){//加载中
            return;
        }
        mTypeSearchView.setLoadState(1);

        if (size<totalCount) {
            // 获取网络数据
            getTypeMovie(type);
        } else {
            // 显示加载到底的提示
            loading =0;
            mTypeSearchView.setLoadState(3);
        }
    }

    /*public void add() {

        new Thread(new Runnable(){
            @Override
            public void run() {
                Map<String,String> movieMap = new HashMap<>();
                movieMap.put("apikey","0b2bdeda43b5688921839c8ecb20399b");
                movieMap.put("city","%E5%8C%97%E4%BA%AC");
                movieMap.put("client","message");//无用参数
                movieMap.put("udid","message");//无用参数

                try{

                for (int i = 0; i < movieData.getSubjects().size(); i++) {
                    MovieApi movieRequest = new RetrofitClient().getMovieService();
                    Call<SubjectInfoBean> getLanguageCall;
                    getLanguageCall=movieRequest.getSubjectInfoCall(movieData.getSubjects().get(i).getId(),movieMap);
                    SubjectInfoBean sub = getLanguageCall.execute().body();

                    Map<String, String> map = new HashMap<>();
                    map.put("movieId",movieData.getSubjects().get(i).getId());
                    map.put("movieTitle",movieData.getSubjects().get(i).getTitle());
                    map.put("movieDirector",movieData.getSubjects().get(i).getDirectors().get(0).getName());
                    map.put("movieYear",movieData.getSubjects().get(i).getYear());
                    map.put("movieLanguage",sub.getLanguages().get(0));
                    String movieType = "";
                    for(int j=0;j<3;j++) {
                        if(j>=movieData.getSubjects().get(i).getGenres().size())
                            break;
                        movieType=movieType + movieData.getSubjects().get(i).getGenres().get(j) +"/";
                    }
                    map.put("movieType",movieType);

                    UserApi request = new RetrofitClient().getUserService();

                    Call<ValueTextBean> addcall;
                    addcall = request.addMovieCall(map);
                    addcall.execute();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }*/

}
