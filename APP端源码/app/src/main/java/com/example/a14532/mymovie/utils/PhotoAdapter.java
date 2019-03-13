package com.example.a14532.mymovie.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14532.mymovie.R;

import java.util.List;
//头像列表Adapter
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>{
    //② 创建ViewHolder
    public static class PhotoViewHolder extends RecyclerView.ViewHolder{
        public final ImageView photoImage;
        public PhotoViewHolder(View v) {
            super(v);
            photoImage = (ImageView) v.findViewById((R.id.photo));
        }
    }
    private List<Integer>  photoData;
    private Context mContext;
    private ImageView showPhoto;
    private TextView photoId;
    public PhotoAdapter(Context context , List<Integer> data,ImageView photo,TextView id){
        this.photoData=data;
        this.mContext=context;
        this.showPhoto=photo;
        this.photoId=id;
    }


    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {

        final int resource =photoData.get(position);
        Glide.with(mContext).load(resource).into(holder.photoImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(mContext).load(resource).into(showPhoto);
                photoId.setText(String.valueOf(resource));
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoData.size();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(v);
    }
}