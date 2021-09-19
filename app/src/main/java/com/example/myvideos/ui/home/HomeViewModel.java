package com.example.myvideos.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.User;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private LiveData<List<User>> usersList;

    public HomeViewModel() {
        usersList = Controller.instance.read();
    }

    public LiveData<List<User>> getUsers() {
        return usersList;
    }

    public User login(String userName, String password) {
        if(usersList.getValue()!=null){
            for (User u: usersList.getValue()) {
                if(u.getName().equals(userName) && u.getPassword().equals(password)) {
                    Controller.instance.setUser(u);
                    break;
                }
            }
        }
        return null;
    }
}