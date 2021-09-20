package com.example.myvideos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myvideos.databinding.ActivityMainBinding;
import com.example.myvideos.model.Controller;
import com.example.myvideos.model.tables.Video;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    DrawerLayout drawer;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_playVideo,
                R.id.nav_myVideosList,
                R.id.nav_myFavoriteVideosList)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart",true);
        if(firstStart)
            insertVideoData();
    }

    private void insertVideoData() {
        for (int i = 1; i < 11; i++) {
            if((i%2)==0) {
                Controller.instance.create(new Video(i + "", false, "Video " + i, false, "android.resource://" + "com.example.myvideos" + "/" + R.raw.videoplayback));
            }
            else{
                Controller.instance.create(new Video(i + "", false, "Video " + i, false, "android.resource://" + "com.example.myvideos" + "/" + R.raw.toystory));
            }
        }
        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_myVideosList:
                navController.navigate(R.id.nav_myVideosList);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_myFavoriteVideosList:
                navController.navigate(R.id.nav_myFavoriteVideosList);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_logOut:
                while(navController.popBackStack());
                //Set user on Model to null
                Controller.instance.setUser(null);
                //Set item menu visible if he is np visible:
                drawer.closeDrawer(GravityCompat.START);
                navController.navigate(R.id.nav_home);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

}