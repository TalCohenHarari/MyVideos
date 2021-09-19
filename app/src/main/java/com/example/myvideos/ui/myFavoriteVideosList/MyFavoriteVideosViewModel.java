package com.example.myvideos.ui.myFavoriteVideosList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.Video;

import java.util.LinkedList;
import java.util.List;

public class MyFavoriteVideosViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videosList;
    public List<Video> list;

    public MyFavoriteVideosViewModel() {
        list = new LinkedList<>();
        videosList=Controller.instance.getAllUserFavoriteVideos(Controller.getUser().getId());
    }

    public MutableLiveData<List<Video>> getVideos() { return videosList; }

}