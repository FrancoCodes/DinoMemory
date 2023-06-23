package com.nubsauce.dinomemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 10000;

    private MediaPlayer mediaPlayer;



    //Variables
    Animation topAnim, bottomAnim;

    ImageView image;
    TextView slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_main);



        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.blackwidow);
        mediaPlayer.start();


        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

    //Hooks
    image = findViewById(R.id.RetroLife);
    slogan = findViewById(R.id.HammerTime);


    image.setAnimation(topAnim);
    slogan.setAnimation(bottomAnim);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(MainActivity.this,fourbyfour.class);
            startActivity(intent);
            finish();
        }
    },SPLASH_SCREEN);

    }


}