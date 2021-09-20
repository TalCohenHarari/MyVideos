package com.example.myvideos.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myvideos.R;
import com.example.myvideos.databinding.FragmentHomeBinding;
import com.example.myvideos.model.Controller;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private View root;
    private EditText userNameEt;
    private EditText  passwordEt;
    private Button loginBtn;
    private TextView newAccountTv;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View Model
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getUsers().observe(getViewLifecycleOwner(),(data)->{ });

        //Initialize
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        Initialize();
        listeners();

        return root;
    }

    private void Initialize() {
        userNameEt = root.findViewById(R.id.login_userName_et);
        passwordEt = root.findViewById(R.id.login_password_et);
        loginBtn = root.findViewById(R.id.login_login_btn);
        newAccountTv = root.findViewById(R.id.login_signUp_tv);
    }

    private void listeners() {
        loginBtn.setOnClickListener(v->{
            if(!userNameEt.getText().toString().isEmpty() && !passwordEt.getText().toString().isEmpty()){
                homeViewModel.login(userNameEt.getText().toString(),passwordEt.getText().toString());
                if(Controller.instance.getUser()!=null){
                    Navigation.findNavController(root).navigate(R.id.nav_myVideosList);
                }
            }
        });
        newAccountTv.setOnClickListener(v->{ Navigation.findNavController(root).navigate(R.id.nav_signUp); });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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