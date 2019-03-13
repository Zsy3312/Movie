package com.example.a14532.mymovie.root;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.FindMovieModel;
import com.example.a14532.mymovie.data.model.MeModel;
import com.example.a14532.mymovie.data.model.OnShowModel;
import com.example.a14532.mymovie.root.findmovie.FindMovieFragment;
import com.example.a14532.mymovie.root.findmovie.FindMoviePresenter;
import com.example.a14532.mymovie.root.me.MeFragment;
import com.example.a14532.mymovie.root.me.MePresenter;
import com.example.a14532.mymovie.root.onshow.OnShowFragment;
import com.example.a14532.mymovie.root.onshow.OnShowPresenter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;


public class RootActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    private CommonTabLayout mTab;

    private FindMoviePresenter mFindMoviePresenter;
    private OnShowPresenter mOnshowPresenter;
    private MePresenter mMePresenter;
    private FindMovieFragment findMovieFragment;
    private OnShowFragment onShowFragment;
    private MeFragment meFragment;

    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_act);
        initView();
    }


    private void initView(){
        mViewPager = (ViewPager)findViewById(R.id.view_main);
        mTab = (CommonTabLayout) findViewById(R.id.tab_main);

        if(null == fragmentList){
            fragmentList = new ArrayList<>();
        }

        if(null  == findMovieFragment){
            findMovieFragment = FindMovieFragment.newInstance();
            fragmentList.add(findMovieFragment);
            mTabEntities.add(new TabEntity("找片",R.drawable.swticondianying,R.drawable.dianying_1));
        }
        FindMovieModel findMovieModel=new FindMovieModel();
        mFindMoviePresenter=new FindMoviePresenter(findMovieFragment,findMovieModel,getApplication());

        if(null == onShowFragment){
            onShowFragment = OnShowFragment.newInstance();
            fragmentList.add(onShowFragment);
            mTabEntities.add(new TabEntity("热映",R.drawable.dianyingpiao,R.drawable.dianying));
        }
        OnShowModel onShowModel=new OnShowModel();
        mOnshowPresenter = new OnShowPresenter(onShowFragment,onShowModel);
        //onShowFragment.initAfterPresenter();

        if(null == meFragment){
            meFragment = MeFragment.newInstance();
            fragmentList.add(meFragment);
            mTabEntities.add(new TabEntity("我的",R.drawable.guanyingren,R.drawable.me));
        }
        MeModel meModel = new MeModel();
        mMePresenter = new MePresenter(meFragment,meModel,getApplication());

        //通过viewPaper绑定fragment
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(viewPagerAdapter);
        mTab.setTabData(mTabEntities);

        mViewPager.setOffscreenPageLimit(3);

        //为tab页的点击添加监听事件
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }
            @Override
            public void onTabReselect(int position) {

            }
        });
        //为viewPager的滑动添加监听事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //viewPager的适配器
    private class ViewPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> fragments;
        private FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragments = fragmentList;
            this.fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
//slidingTab
/*
public class RootActivity extends AppCompatActivity {
    SlidingTabLayout tablayout;

    ViewPager viewPager;

    private FragmentManager manage;

    private ArrayList<Fragment> mFagments = new ArrayList<>();
    private String[] mTitles = {"热映", "选片", "我的"};

    private MyPagerAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_act);

        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.view_pager);
        initView();
    }

    private void initView() {
        mFagments.add(MeFragment.getInstance(mTitles[0]));
        mFagments.add(MeFragment.getInstance(mTitles[1]));
        mFagments.add(MeFragment.getInstance(mTitles[2]));
        //getChildFragmentManager() 如果是嵌套在fragment中就要用这个
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setViewPager(viewPager, mTitles);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public int getCount() {
            return mFagments.size();
        }

        @Override public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override public Fragment getItem(int position) {
            return mFagments.get(position);
        }
    }
}*/

