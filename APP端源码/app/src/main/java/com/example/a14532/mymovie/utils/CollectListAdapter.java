package com.example.a14532.mymovie.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.remote.bean.CollectMovieBean;
import com.example.a14532.mymovie.subject.SubjectActivity;

//收藏，浏览记录列表Adapter
public class CollectListAdapter extends RecyclerView.Adapter<CollectListAdapter.MovieViewHolder>{
    //② 创建ViewHolder
    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        public final TextView title;
        public final TextView date;
        public MovieViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.collect_title);
            date =(TextView) v.findViewById(R.id.collect_date);
        }
    }

    private CollectMovieBean movieData;
    private Context mContext;
    public CollectListAdapter(Context context , CollectMovieBean data){
        this.movieData=data;
        this.mContext=context;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.title.setText(movieData.getSubjects().get(position).getMovieTitle());
        String data="收藏日期：" + movieData.getSubjects().get(position).getTime();
        holder.date.setText(data);
        final String movieID=movieData.getSubjects().get(position).getMovieId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubjectActivity.class);
                intent.putExtra("id",movieID);//activity之间传递参数
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieData.getSubjects().size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_item, parent, false);
        return new MovieViewHolder(v);
    }
}