package com.example.a14532.mymovie.photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.utils.PhotoAdapter;

import java.util.List;

public class PhotoFragment extends Fragment implements PhotoContract.View {
    private ImageView photo;
    private PhotoContract.Presenter mPresenter;
    private Button updatePhoto;

    private TextView photoId;
    RecyclerView movieRecyclerView;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(PhotoContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.photo_fra, container,false);

        photo=root.findViewById(R.id.imagePhoto);
        photoId=root.findViewById(R.id.paraTextView);
        photoId.setText(String.valueOf(R.drawable.guanyanren));

        updatePhoto=root.findViewById(R.id.update_photo_button);
        updatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setPhoto(photoId.getText().toString());
            }
        });

        movieRecyclerView = (RecyclerView) root.findViewById(R.id.photo_recycler_view);
        // 设置布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        movieRecyclerView.setLayoutManager(layoutManager);
        mPresenter.initView();
        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapter(List<Integer> photoList){

        movieRecyclerView.setAdapter(new PhotoAdapter(this.getContext(),photoList,photo,photoId));
    }

    @Override
    public void showPhoto(int id){
        Glide.with(this).load(id).into(photo);
    }
}