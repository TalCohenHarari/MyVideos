package com.example.myvideos.ui.MyVideosList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.User;
import com.example.myvideos.model.tables.Video;

import java.util.LinkedList;
import java.util.List;

public class MyVideosViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videosList;
    public List<Video> list;

    public MyVideosViewModel() {
        list = new LinkedList<>();
        videosList=Controller.instance.getAllUserVideos(Controller.getUser().getId());
    }

    public MutableLiveData<List<Video>> getVideos() { return videosList; }

}