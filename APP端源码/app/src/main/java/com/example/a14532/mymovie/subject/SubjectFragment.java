package com.example.a14532.mymovie.subject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.ddz.floatingactionbutton.FloatingActionButton;
import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.remote.bean.SubjectInfoBean;
import com.example.a14532.mymovie.login.LoginActivity;
import com.example.a14532.mymovie.utils.CommentAdapter;


import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends Fragment implements SubjectContract.View {

    private SubjectContract.Presenter mPresenter;

    private RecyclerView commentRecyclerView;

    private ConvenientBanner convenientBanner;//顶部广告栏控件

    private TextView Title;
    private TextView YearAndType;
    private TextView OnShowTime;
    private TextView Length;
    private TextView Rating;
    private RatingBar RantingStar;
    private TextView SubjectSubscribe;

    private TextView Director;
    private TextView ActorOne;
    private TextView ActorTwo;
    private TextView ActorThree;
    private List<TextView> Actor;

    private ImageView DirectorImage;
    private ImageView ActorImageOne;
    private ImageView ActorImageTwo;
    private ImageView ActorImageThree;
    private List<ImageView> ActorImage;

    private FloatingActionButton collectButton;
    private FloatingActionButton refreshButton;
    private FloatingActionButton clearHistoryButton;

    private static final String ARGUMENT_MOVIE_ID = "TASK_ID";

    public static SubjectFragment newInstance(String movieID) {
        //官方推荐的fragment传参
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_MOVIE_ID, movieID);
        SubjectFragment fragment = new SubjectFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(SubjectContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.subject_fra, container,false);

        collectButton=root.findViewById(R.id.collect_button);
        collectButton.setOnClickListener(new collectListener());

        clearHistoryButton=root.findViewById(R.id.delete_history_button);
        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteHistory();
            }
        });

        refreshButton=root.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//refresh按钮刷新界面
                mPresenter.getMovieSubjectInfo();
                mPresenter.getCollectOrNot();
            }
        });

        convenientBanner = (ConvenientBanner) root.findViewById(R.id.convenientBanner);
        Title =root.findViewById(R.id.subjectTitle);
        YearAndType =root.findViewById(R.id.subjectYearAndType);
        OnShowTime =root.findViewById(R.id.subjectOnShowTime);
        Length =root.findViewById(R.id.subjectLength);
        Rating = root.findViewById(R.id.subjectRating);
        RantingStar=root.findViewById(R.id.ratingBar);
        SubjectSubscribe=root.findViewById(R.id.subjectSubscribe);

        Director=root.findViewById(R.id.directorName);
        ActorOne=root.findViewById(R.id.actorNameOne);
        ActorTwo=root.findViewById(R.id.actorNameTwo);
        ActorThree=root.findViewById(R.id.actorNameThree);
        DirectorImage=root.findViewById(R.id.directorImage);
        ActorImageOne=root.findViewById(R.id.actorImageOne);
        ActorImageTwo=root.findViewById(R.id.actorImageTwo);
        ActorImageThree=root.findViewById(R.id.actorImageThree);
        Actor= new ArrayList<>();
        Actor.add(ActorOne);
        Actor.add(ActorTwo);
        Actor.add(ActorThree);
        ActorImage = new ArrayList<>();
        ActorImage.add(ActorImageOne);
        ActorImage.add(ActorImageTwo);
        ActorImage.add(ActorImageThree);

        commentRecyclerView = (RecyclerView) root.findViewById(R.id.comment_recycler_view);
        // 设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerView.setLayoutManager(layoutManager);

        mPresenter.getMovieSubjectInfo();
        mPresenter.getCollectOrNot();

        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void initView(List<String> networkImages,SubjectInfoBean dataBean){

        commentRecyclerView.setAdapter(new CommentAdapter(this.getContext(),dataBean));

        convenientBanner.setPages(new CBViewHolderCreator<NetWorkImageHolderView>() {
           @Override
            public NetWorkImageHolderView createHolder() {
                return new NetWorkImageHolderView();
            }
        },networkImages)
                .setPageIndicator(new int[]{R.drawable.d2, R.drawable.d1})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        Title.setText(dataBean.getTitle());

        String yearAndType = dataBean.getYear()+"/";
        for(int i=0;i<3;i++) {
            if(i>=dataBean.getGenres().size())
                break;
            yearAndType=yearAndType + dataBean.getGenres().get(i) +"/";
        }
        YearAndType.setText(yearAndType);

        RantingStar.setRating((float) dataBean.getRating().getAverage()/2);

        String rating= String.valueOf(dataBean.getRating().getAverage());
        Rating.setText(rating);

        String onShowTime ="上映日期 ："+dataBean.getPubdate();
        OnShowTime.setText(onShowTime);

        String durations = dataBean.getDurations().toString();
        String duration = "电影时长 ：" +durations.substring(1, durations.length() - 1);
        Length.setText(duration);

        String subjectSubscribe = "    "+dataBean.getSummary();
        SubjectSubscribe.setText(subjectSubscribe);
        if(dataBean.getDirectors().size()>0) {
            String director = dataBean.getDirectors().get(0).getName() + "(导演)";
            Director.setText(director);
            if(dataBean.getDirectors().get(0).getAvatars()!=null)
            {
                Glide.with(this).load(dataBean.getDirectors().get(0).getAvatars().getMedium()).into(DirectorImage);
            }
        }
        for(int i=0;i<3;i++) {
            if(i>=dataBean.getCasts().size())
                break;
            Actor.get(i).setText(dataBean.getCasts().get(i).getName());
            if(dataBean.getCasts().get(i).getAvatars()!=null)
            {
                Glide.with(this).load(dataBean.getCasts().get(i).getAvatars().getMedium()).into(ActorImage.get(i));
            }
        }
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
//        //开始自动翻页
        convenientBanner.startTurning(3000);
        mPresenter.getCollectOrNot();
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
//        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void showLoginAct(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showCancelCollectButton(){
        collectButton.setIcon(R.drawable.cancalcollect);
        collectButton.setOnClickListener(new cancelCollectListener());
    }

    @Override
    public void showCollectButton(){
        collectButton.setIcon(R.drawable.shoucang);
        collectButton.setOnClickListener(new collectListener());
    }

    class collectListener  implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mPresenter.collectMovie();
            collectButton.setOnClickListener(new nullListener());
        }
    }

    class cancelCollectListener  implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mPresenter.cancelCollect();
            collectButton.setOnClickListener(new nullListener());
        }
    }
    class nullListener implements View.OnClickListener{
        @Override
        public void onClick(View v){

        }
    }
}
