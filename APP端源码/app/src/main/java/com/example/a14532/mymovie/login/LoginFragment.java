package com.example.a14532.mymovie.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a14532.mymovie.R;
import com.example.a14532.mymovie.register.RegisterActivity;

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private Button btn_login;
    private Button btn_register;
    private EditText username;
    private EditText password;



    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter){
        mPresenter=presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fra, container,false);
        btn_login = (Button) root.findViewById(R.id.login_button);
        btn_register = (Button) root.findViewById(R.id.register_button);
        username = (EditText) root.findViewById(R.id.username_in);
        password = (EditText) root.findViewById(R.id.password_in);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(username.getText().toString(),password.getText().toString());
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toRegisterAct();
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

    @Override
    public void showRegisterAct(){
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
