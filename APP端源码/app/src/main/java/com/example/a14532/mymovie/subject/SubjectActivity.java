package com.example.a14532.mymovie.subject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.data.model.SubjectModel;
import com.example.a14532.mymovie.utils.ActivityUtils;

public class SubjectActivity extends AppCompatActivity {
    SubjectPresenter mSubjectPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_act);

        Intent intent=getIntent();
        String movieID =intent.getStringExtra("id");

        SubjectFragment subjectFragment = (SubjectFragment) getSupportFragmentManager()
                .findFragmentById(R.id.subjectContentFrame);

        if (subjectFragment == null) {
            subjectFragment = SubjectFragment.newInstance(movieID);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    subjectFragment, R.id.subjectContentFrame);
        }
       SubjectModel subjectModel=new SubjectModel();

        // Create the presenter
        mSubjectPresenter = new SubjectPresenter(subjectFragment,subjectModel,movieID,getApplication());
    }

}
