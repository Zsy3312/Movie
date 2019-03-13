package com.example.a14532.mymovie.register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a14532.mymovie.R;

import java.util.ArrayList;

public class RegisterFragment extends Fragment implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;

    private Button btn_register;
    private EditText username;
    private EditText password;
    private EditText passwordConfirm;
    private Spinner spinner_sex;
    private Spinner spinner_year;
    private Spinner spinner_study;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.register_fra, container,false);

        username=root.findViewById(R.id.register_username_in);
        password=root.findViewById(R.id.register_password_in);
        passwordConfirm=root.findViewById(R.id.register_password_confirm);
        btn_register = root.findViewById(R.id.register_button);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.register(username.getText().toString(),password.getText().toString(),passwordConfirm.getText().toString());
            }
        });

        spinner_sex = (Spinner)root.findViewById(R.id.spinner_sex);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
                mPresenter.setSex(getResources().getStringArray(R.array.spinner_sex)[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_year = (Spinner)root.findViewById(R.id.spinner_year);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
                mPresenter.setYear(getResources().getStringArray(R.array.spinner_year)[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_study = (Spinner)root.findViewById(R.id.spinner_study);
        spinner_study.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
                mPresenter.setStudy(getResources().getStringArray(R.array.spinner_study)[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void back()
    {
        this.getActivity().finish();
    }
}
