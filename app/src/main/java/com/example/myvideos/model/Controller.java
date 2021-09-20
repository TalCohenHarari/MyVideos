package com.example.myvideos.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.myvideos.model.tables.UserVideoCrossRef;
import com.example.myvideos.model.tables.Video;
import com.example.myvideos.model.tables.User;
import com.example.myvideos.model.tables.VideoWithUser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    final public static Controller instance = new Controller();
    ExecutorService executorService = Executors.newCachedThreadPool();

    public static User user;
    public static User getUser() { return user; }
    public static void setUser(User user) { Controller.user = user; }

    //----------------------------------USER CRUD----------------------------------------
    private  LiveData<List<User>> allCustomers= AppLocalDB.db.userDao().getAll();

    public void create(User user){
        executorService.execute(()-> {
            AppLocalDB.db.userDao().insertAll(user);
        });
        setUser(user);
    }

    public LiveData<List<User>> read(){ return allCustomers; }

    public void update(User user){ executorService.execute(()->AppLocalDB.db.userDao().insertAll(user)); }

    public void delete(User user){ executorService.execute(()->AppLocalDB.db.userDao().delete(user)); }

    //----------------------------------video CRUD----------------------------------------

    private  MutableLiveData<List<Video>> videosList = new MutableLiveData<>();

    private  MutableLiveData<List<Video>> favoriteVideosList = new MutableLiveData<>();

    public void create(Video video){ executorService.execute(()->AppLocalDB.db.videoDao().insertAll(video)); }

    public void update(Video video){ executorService.execute(()-> {
        AppLocalDB.db.videoDao().insertAll(video);
        getAllUserVideos(Controller.getUser().getId());
        getAllUserFavoriteVideos(Controller.getUser().getId());
    });
    }

    public void delete(Video video){ executorService.execute(()->AppLocalDB.db.videoDao().delete(video)); }

    public MutableLiveData<List<Video>> getAllUserVideos(String userId){
        executorService.execute(()->videosList.postValue(AppLocalDB.db.videoUserDao().getAllUserVideos(userId)));
        return videosList;
    }

    public MutableLiveData<List<Video>> getAllUserFavoriteVideos(String userId) {
        executorService.execute(()->favoriteVideosList.postValue(AppLocalDB.db.videoUserDao().getAllFavoriteUserVideos(userId)));
        return favoriteVideosList;
    }

    //----------------------------------UserVideoCrossRef CRUD-----------------------------------------

    public void create(UserVideoCrossRef userVideoCrossRef){ executorService.execute(()->AppLocalDB.db.videoUserDao().insertAll(userVideoCrossRef)); }

    public void delete(String userId,String videoId){ executorService.execute(()-> {
            AppLocalDB.db.videoUserDao().delete(userId, videoId);
            getAllUserVideos(userId);
            getAllUserFavoriteVideos(userId);
        });
    }
}
