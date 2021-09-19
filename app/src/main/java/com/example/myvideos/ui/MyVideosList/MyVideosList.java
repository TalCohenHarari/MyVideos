package com.example.myvideos.ui.MyVideosList;

import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.service.controls.Control;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myvideos.MyApplication;
import com.example.myvideos.R;
import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.User;
import com.example.myvideos.model.tables.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MyVideosList extends Fragment {

    View root;
    RecyclerView recyclerView;
    MyAdapter adapter;
    MyVideosViewModel myVideosViewModel;
    public static String packageName;
    Dialog editDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_my_videos_list, container, false);
        initialize();
        listeners();
        return root;
    }

    private void initialize() {

        packageName = "com.example.myvideos";
        editDialog = new Dialog(getContext());

        //List
        recyclerView = root.findViewById(R.id.mainPage_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.context);
        recyclerView.setLayoutManager(manager);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        //ViewModel
        myVideosViewModel = new ViewModelProvider(this).get(MyVideosViewModel.class);
        myVideosViewModel.getVideos().observe(getViewLifecycleOwner(), (data)->{
                myVideosViewModel.list = data;
                adapter.notifyDataSetChanged();
        });
    }

    private void editPopUpDialog(Video video) {
        editDialog.setContentView(R.layout.edit_dialog);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            editDialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.popup_dialog_background));
        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        editDialog.setCancelable(true);
        editDialog.getWindow().getAttributes().windowAnimations = R.style.popup_dialog_animation;

        EditText videoName = editDialog.findViewById(R.id.popupEditDialog_videoName_et);
        Button saveBtn = editDialog.findViewById(R.id.popupEditDialog_save_btn);

        saveBtn.setOnClickListener(v->{
            if(!videoName.getText().toString().isEmpty()){
                video.setVideoName(videoName.getText().toString());
                Controller.instance.update(video);
                adapter.notifyDataSetChanged();
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }

    private void listeners() {

        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position,String id) {
                MyVideosListDirections.ActionNavMyVideosListToNavPlayVideo2 action= MyVideosListDirections.actionNavMyVideosListToNavPlayVideo2(id);
                Navigation.findNavController(root).navigate(action);
            }
            @Override
            public void onDeleteClick(int position) {

            }

            @Override
            public void onStarClick(int position, String id) {
                Video v = myVideosViewModel.list.get(position);
                String toast = "";
                if(!v.isFavorite()) {
                    v.setFavorite(true);
                    toast = "Video saved in favorite list";
                }
                else{
                    v.setFavorite(false);
                    toast = "Video unsaved in favorite list";
                }
                Controller.instance.update(v);
                Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEditClick(int position, String id) {
                Video v = myVideosViewModel.list.get(position);
                editPopUpDialog(v);
            }
        });
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        OnItemClickListener listener;
        String id;
        TextView name;
        VideoView videoView;
        ImageView delete;
        ImageView star;
        ImageView edit;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.videoListRowRow_Name_tv);
            videoView = itemView.findViewById(R.id.videoListRowRow_viewo_vV);
            delete = itemView.findViewById(R.id.videoListRowRow_delete_imgV);
            star = itemView.findViewById(R.id.videoListRowRow_star_imgV);
            edit = itemView.findViewById(R.id.videListRow_edit_imageView);

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

            star.setOnClickListener(v -> {
                if(listener!=null){
                    int position=getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION)
                        listener.onStarClick(position,id);
                }
            });

            edit.setOnClickListener(v->{
                if(listener!=null){
                    int position=getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION)
                        listener.onEditClick(position,id);
                }
            });
        }

        public void bind(Video video){
            id = video.getId();
            name.setText(video.getVideoName() +" (" + video.getUserName() + ")");
            String path = "android.resource://" + packageName + "/" + R.raw.videoplayback;
            Uri uri = Uri.parse(path);
            videoView.setVideoURI(uri);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position, String id);
        void onDeleteClick(int position);
        void onStarClick(int position, String id);
        void onEditClick(int position, String id);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        OnItemClickListener listener;

        public void setOnClickListener(OnItemClickListener listener){
            this.listener=listener;
        }
        //Create a viewHolder for the view:
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.videos_row,parent,false);
            MyViewHolder holder= new MyViewHolder(view,listener);
            return holder;
        }
        // make the variables bind to the created view from "onCreateViewHolder" function:
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Video video = myVideosViewModel.list.get(position);
            holder.bind(video);
        }
        //Give me the items count:
        @Override
        public int getItemCount() {
            return myVideosViewModel.list.size();
        }
    }

}