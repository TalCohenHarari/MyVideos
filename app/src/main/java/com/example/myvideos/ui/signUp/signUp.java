package com.example.myvideos.ui.signUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myvideos.R;
import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.User;
import com.example.myvideos.model.tables.UserVideoCrossRef;
import com.example.myvideos.model.tables.Video;
import com.example.myvideos.ui.home.HomeViewModel;

import java.util.UUID;


public class signUp extends Fragment {

    private View root;
    private EditText userNameEt;
    private EditText  passwordEt;
    private Button signUpBtn;
    private TextView existAccountTv;
    private SignUpViewModel signUpViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Initialize();
        listeners();

        return root;
    }

    private void Initialize() {
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        signUpViewModel.getUsers().observe(getViewLifecycleOwner(),(data)->{ });

        userNameEt = root.findViewById(R.id.signUp_userName_et);
        passwordEt = root.findViewById(R.id.signUp_password_et);
        signUpBtn = root.findViewById(R.id.signUp_signUp_btn);
        existAccountTv = root.findViewById(R.id.signUp_login_tv);
    }

    private void listeners() {
        signUpBtn.setOnClickListener(v->{
            if(!signUpViewModel.isUserExist(userNameEt.getText().toString(),passwordEt.getText().toString()) &&
                    !userNameEt.getText().toString().isEmpty() &&
                    !passwordEt.getText().toString().isEmpty())
            {
                    String userId = UUID.randomUUID().toString();
                    User user = new User(userId, userNameEt.getText().toString(), passwordEt.getText().toString(), true);
                    Controller.instance.create(user);

                    for (int i = 1; i < 10; i++) {
                        Controller.instance.create(new UserVideoCrossRef(userId, i + ""));
                    }
                    Navigation.findNavController(root).navigate(R.id.nav_myVideosList);
                }

        });
        existAccountTv.setOnClickListener(v->{ Navigation.findNavController(root).navigate(R.id.nav_home); });
    }

    @Override
    public void onResume() {
        super.onResume();
        if( ((AppCompatActivity)getActivity()).getSupportActionBar()!=null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( ((AppCompatActivity)getActivity()).getSupportActionBar()!=null)
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}