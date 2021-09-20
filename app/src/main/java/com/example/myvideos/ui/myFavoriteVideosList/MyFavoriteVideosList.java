package com.example.myvideos.ui.myFavoriteVideosList;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.myvideos.MyApplication;
import com.example.myvideos.R;
import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.Video;
import com.example.myvideos.ui.MyVideosList.MyVideosList;
import com.example.myvideos.ui.MyVideosList.MyVideosListDirections;
import com.example.myvideos.ui.MyVideosList.MyVideosViewModel;


public class MyFavoriteVideosList extends Fragment {


    View root;
    RecyclerView recyclerView;
    MyAdapter adapter;
    MyFavoriteVideosViewModel myFavoriteVideosViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_my_favorite_videos_list, container, false);
        initialize();
        listeners();
        return root;
    }

    private void initialize() {

        //List
        recyclerView = root.findViewById(R.id.favoriteVideos_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.context);
        recyclerView.setLayoutManager(manager);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        //ViewModel
        myFavoriteVideosViewModel = new ViewModelProvider(this).get(MyFavoriteVideosViewModel.class);
        myFavoriteVideosViewModel.getVideos().observe(getViewLifecycleOwner(), (data)->{
            myFavoriteVideosViewModel.list = data;
            adapter.notifyDataSetChanged();
        });
    }

    private void listeners() {

        adapter.setOnClickListener(new MyFavoriteVideosList.OnItemClickListener() {
            @Override
            public void onClick(int position,String id) {
                MyFavoriteVideosListDirections.ActionNavMyFavoriteVideosListToNavPlayVideo action= MyFavoriteVideosListDirections.actionNavMyFavoriteVideosListToNavPlayVideo(id);
                Navigation.findNavController(root).navigate(action);
            }
            @Override
            public void onDeleteClick(int position) {
                Video v = myFavoriteVideosViewModel.list.get(position);
                Controller.instance.delete(Controller.getUser().getId(),v.getId());
                adapter.notifyDataSetChanged();
            }
        });
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        MyFavoriteVideosList.OnItemClickListener listener;
        String id;
        TextView name;
        VideoView videoView;
        ImageView delete;

        public MyViewHolder(@NonNull View itemView, MyFavoriteVideosList.OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.favoriteVideoListRowRow_Name_tv);
            videoView = itemView.findViewById(R.id.favoriteVideoListRowRow_viewo_vV);
            delete = itemView.findViewById(R.id.favoriteVideoListRowRow_delete_imgV);
            this.listener=listener;

            itemView.setOnClickListener(v -> {
                if(listener!=null){
                    int position=getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION)
                        listener.onClick(position,id);
                }
            });

            delete.setOnClickListener(v -> {
                if(listener!=null){
                    int position=getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION)
                        listener.onDeleteClick(position);
                }
            });
        }

        public void bind(Video video){
            id = video.getId();
            name.setText(video.getVideoName());
            String path = video.getVideoPath();
            Uri uri = Uri.parse(path);
            videoView.setVideoURI(uri);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position, String id);
        void onDeleteClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        MyFavoriteVideosList.OnItemClickListener listener;

        public void setOnClickListener(MyFavoriteVideosList.OnItemClickListener listener){
            this.listener=listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.favorite_videos_row,parent,false);
            MyViewHolder holder= new MyViewHolder(view,listener);
            return holder;
        }
        // make the variables bind to the created view from "onCreateViewHolder" function:
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Video video = myFavoriteVideosViewModel.list.get(position);
            holder.bind(video);
        }
        //Give me the items count:
        @Override
        public int getItemCount() {
            return myFavoriteVideosViewModel.list.size();
        }
    }
}