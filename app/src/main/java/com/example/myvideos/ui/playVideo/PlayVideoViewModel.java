package com.example.myvideos.ui.playVideo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.Video;

import java.util.LinkedList;
import java.util.List;

public class PlayVideoViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videosList;
    public Video video;

    public PlayVideoViewModel() {
        videosList=Controller.instance.getAllUserVideos(Controller.getUser().getId());
    }

    public MutableLiveData<List<Video>> getVideos() { return videosList; }

    public void getCurrentVideo(String videoId){
        for (Video v:videosList.getValue()) {
            if(v.getId().equals(videoId)) {
                video=v;
                break;
            }
        }
    }
}