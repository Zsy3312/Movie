package com.example.a14532.mymovie.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.remote.bean.SubjectInfoBean;
//评论列表Adapter
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        public final TextView userName;
        public final TextView comment;
        public final TextView useCount;
        public final ImageView userPhoto;
        public final RatingBar RantingStar;
        public CommentViewHolder(View v) {
            super(v);
            userName = (TextView) v.findViewById(R.id.userName);
            comment =(TextView) v.findViewById(R.id.comment);
            useCount = (TextView) v.findViewById(R.id.useCount);
            userPhoto = (ImageView) v.findViewById((R.id.userPhoto));
            RantingStar =(RatingBar) v.findViewById(R.id.commentRatingBar);
        }
    }

    private SubjectInfoBean commentData;
    private Context mContext;
    public CommentAdapter(Context context , SubjectInfoBean data){
        this.commentData=data;
        this.mContext=context;
    }


    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.userName.setText(commentData.getPopular_comments().get(position).getAuthor().getName());
        holder.comment.setText(commentData.getPopular_comments().get(position).getContent());

        holder.useCount.setText(String.valueOf(commentData.getPopular_comments().get(position).getUseful_count()));

        Glide.with(mContext).load(commentData.getPopular_comments().get(position).getAuthor().getAvatar()).into(holder.userPhoto);

        holder.RantingStar.setRating((float)commentData.getPopular_comments().get(position).getRating().getValue());
    }

    @Override
    public int getItemCount() {
        return commentData.getPopular_comments().size();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(v);
    }
}
