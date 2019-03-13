package com.example.a14532.mymovie.root.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.login.LoginActivity;
import com.example.a14532.mymovie.moviecount.MovieCountActivity;
import com.example.a14532.mymovie.movielist.MovieListActivity;
import com.example.a14532.mymovie.photo.PhotoActivity;
import com.example.a14532.mymovie.usercount.UserCountActivity;


public class MeFragment extends Fragment implements MeContract.View {

    private MeContract.Presenter mPresenter;
    TextView userName;
    TextView collect;
    TextView history;
    TextView changePhoto;
    TextView quitLogin;
    ImageView userPhoto;

    TextView userCount;
    TextView movieCount;
    TextView hideTextOne;
    TextView hideTextTwo;
    ConstraintLayout userCountLayout;
    ConstraintLayout movieCountLayout;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(MeContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.me_fra, container,false);
        collect =root.findViewById(R.id.my_collect);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toCollectAct();
            }
        });
        history =root.findViewById(R.id.my_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toHistoryAct();
            }
        });
        changePhoto=root.findViewById(R.id.change_photo);
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toChangePhotoAct();
            }
        });
        userName=root.findViewById(R.id.meUserName);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toLoginAct();
            }
        });
        userPhoto=root.findViewById(R.id.meUserPhoto);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toLoginAct();
            }
        });
        quitLogin=root.findViewById(R.id.quitLogin);
        quitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.quitLogin();
            }
        });
        //管理员部分
        userCount=root.findViewById(R.id.user_count);
        userCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toUserCountAct();
            }
        });
        movieCount=root.findViewById(R.id.movie_count);
        movieCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toMovieCountAct();
            }
        });
        userCountLayout=root.findViewById(R.id.user_count_layout);
        movieCountLayout=root.findViewById(R.id.movie_count_layout);
        hideTextOne=root.findViewById(R.id.hide_text_one);
        hideTextTwo=root.findViewById(R.id.hide_text_two);
        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoginAct(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        //intent.putExtra("id","1652693");activity之间传递参数
        startActivity(intent);
    }

    @Override
    public void showCollectAct(){
        Intent intent = new Intent(getContext(), MovieListActivity.class);
        intent.putExtra("path","collect");
        startActivity(intent);
    }
    @Override
    public void showHistoryAct(){
        Intent intent = new Intent(getContext(), MovieListActivity.class);
        intent.putExtra("path","history");
        startActivity(intent);
    }
    @Override
    public void showChangePhotoAct(){
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.initUserInfo();
    }

    @Override
    public void showUserInfo(String name,int id){
        userName.setText(name);
        Glide.with(this).load(id).into(userPhoto);
    }

    @Override
    public void showAdminAct(){
        userCountLayout.setVisibility(View.VISIBLE);
        movieCountLayout.setVisibility(View.VISIBLE);
        hideTextOne.setVisibility(View.VISIBLE);
        hideTextTwo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAdminAct(){
        userCountLayout.setVisibility(View.GONE);
        movieCountLayout.setVisibility(View.GONE);
        hideTextOne.setVisibility(View.GONE);
        hideTextTwo.setVisibility(View.GONE);
    }

    @Override
    public void showUserCountAct(){
        Intent intent = new Intent(getContext(), UserCountActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMovieCountAct(){
        Intent intent = new Intent(getContext(), MovieCountActivity.class);
        startActivity(intent);
    }
}