package com.example.a14532.mymovie.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.remote.bean.MovieBean;
import com.example.a14532.mymovie.subject.SubjectActivity;
//电影列表Adapter
public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;


    //② 创建ViewHolder
    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        public final TextView title;
        public final TextView star;
        public final TextView type;
        public final TextView actor;
        public final TextView director;
        public final ImageView movieImage;
        public MovieViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.movie_title);
            star =(TextView) v.findViewById(R.id.movie_star);
            type = (TextView) v.findViewById(R.id.movie_type);
            actor =(TextView) v.findViewById(R.id.movie_actor);
            director=(TextView) v.findViewById(R.id.movie_director);
            movieImage = (ImageView) v.findViewById((R.id.movie_image));
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    private MovieBean movieData;
    private Context mContext;
    public MovieAdapter(Context context , MovieBean data){
        this.movieData=data;
        this.mContext=context;
    }

    @Override
    public int getItemViewType(int position){
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    /*
    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.title.setText(movieData.getSubjects().get(position).getTitle());
        holder.star.setText(String.valueOf(movieData.getSubjects().get(position).getRating().getAverage()));

        String genres = movieData.getSubjects().get(position).getGenres().toString();
        String genre = genres.substring(1, genres.length() - 1);
        holder.type.setText(genre);

        String director="导演：" ;
        if(movieData.getSubjects().get(position).getDirectors().size()>0)
            director = director +movieData.getSubjects().get(position).getDirectors().get(0).getName();
        holder.director.setText(director);

        String actors="主演：";
        for(int i=0;i<3;i++) {
            if(i>=movieData.getSubjects().get(position).getCasts().size())
                break;
            actors=actors + movieData.getSubjects().get(position).getCasts().get(i).getName() +"/";
        }
        holder.actor.setText(actors);

        Glide.with(mContext).load(movieData.getSubjects().get(position).getImages().getMedium()).into(holder.movieImage);

        final String movieID=movieData.getSubjects().get(position).getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubjectActivity.class);
                intent.putExtra("id",movieID);//activity之间传递参数
                mContext.startActivity(intent);
            }
        });
    }
    */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.title.setText(movieData.getSubjects().get(position).getTitle());
            movieViewHolder.star.setText(String.valueOf(movieData.getSubjects().get(position).getRating().getAverage()));

            String genres = movieData.getSubjects().get(position).getGenres().toString();
            String genre = genres.substring(1, genres.length() - 1);
            movieViewHolder.type.setText(genre);

            String director="导演：" ;
            if(movieData.getSubjects().get(position).getDirectors().size()>0)
                director = director +movieData.getSubjects().get(position).getDirectors().get(0).getName();
            movieViewHolder.director.setText(director);

            String actors="主演：";
            for(int i=0;i<3;i++) {
                if(i>=movieData.getSubjects().get(position).getCasts().size())
                    break;
                actors=actors + movieData.getSubjects().get(position).getCasts().get(i).getName() +"/";
            }
            movieViewHolder.actor.setText(actors);

            Glide.with(mContext).load(movieData.getSubjects().get(position).getImages().getMedium()).into(movieViewHolder.movieImage);

            final String movieID=movieData.getSubjects().get(position).getId();
            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SubjectActivity.class);
                    intent.putExtra("id",movieID);//activity之间传递参数
                    mContext.startActivity(intent);
                }
            });

        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieData.getSubjects().size()+1;
    }
    /*
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(v);

    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_item, parent, false);
            return new MovieViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}