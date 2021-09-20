package com.example.myvideos.ui.playVideo;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.myvideos.MyApplication;
import com.example.myvideos.R;
import com.example.myvideos.model.tables.Video;
import com.example.myvideos.ui.MyVideosList.MyVideosViewModel;


public class playVideo extends Fragment {


    View root;
    PlayVideoViewModel playVideoViewModel;
    VideoView videoView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_play_video, container, false);
        videoView = root.findViewById(R.id.playVideo_videoView);

        //ViewModel
        playVideoViewModel = new ViewModelProvider(this).get(PlayVideoViewModel.class);

        playVideoViewModel.getVideos().observe(getViewLifecycleOwner(), (data)->{
            if(data!=null) {
                String videoId = playVideoArgs.fromBundle(getArguments()).getVideoId();
                playVideoViewModel.getCurrentVideo(videoId);
                String path = playVideoViewModel.video.getVideoPath();
                Uri uri = Uri.parse(path);
                videoView.setVideoURI(uri);
                MediaController mediaController = new MediaController(getContext());
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.start();
            }
        });

        return root;
    }
}