package com.example.myvideos.ui.signUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myvideos.R;
import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.User;
import com.example.myvideos.model.tables.Video;

import java.util.UUID;


public class signUp extends Fragment {

    private View root;
    private EditText userNameEt;
    private EditText  passwordEt;
    private Button signUpBtn;
    private TextView existAccountTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Initialize();
        listeners();

        return root;
    }

    private void Initialize() {
        userNameEt = root.findViewById(R.id.signUp_userName_et);
        passwordEt = root.findViewById(R.id.signUp_password_et);
        signUpBtn = root.findViewById(R.id.signUp_signUp_btn);
        existAccountTv = root.findViewById(R.id.signUp_login_tv);
    }

    private void listeners() {
        signUpBtn.setOnClickListener(v->{
            //TODO: check if there is this user name on system
            String userId = UUID.randomUUID().toString();
            User user = new User(userId,userNameEt.getText().toString(),passwordEt.getText().toString(),true);
            Controller.instance.create(user);

            //(String id, String userName, String userOwnerId, boolean isDeleted,String videoName,boolean isFavorite)
            for (int i = 1; i < 6; i++) {
                Controller.instance.create(new Video(
                        UUID.randomUUID().toString(),
                        userNameEt.getText().toString(),
                        userId,
                        false,
                        "Video Number "+i,
                        false,
                        "com.example.myvideos"
                ));
            }
            Navigation.findNavController(root).navigate(R.id.nav_myVideosList);
        });
        existAccountTv.setOnClickListener(v->{ Navigation.findNavController(root).navigate(R.id.nav_home); });
    }
}