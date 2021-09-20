package com.example.myvideos.ui.signUp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.User;

import java.util.List;

public class SignUpViewModel extends ViewModel {

    private LiveData<List<User>> usersList;

    public SignUpViewModel() {
        usersList = Controller.instance.read();
    }

    public LiveData<List<User>> getUsers() {
        return usersList;
    }

    public boolean isUserExist(String userName, String password) {
        if(usersList.getValue()!=null){
            for (User u: usersList.getValue()) {
                if(u.getName().equals(userName) && u.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}