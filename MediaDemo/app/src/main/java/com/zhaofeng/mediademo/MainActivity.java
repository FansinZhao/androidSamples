package com.zhaofeng.mediademo;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int audoId;

    private Button song;

    private MediaPlayer player;

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPool =(new SoundPool.Builder()).build();
        audoId = soundPool.load(this,R.raw.music,1);
        findViewById(R.id.btnMusic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               soundPool.play(audoId,1,1,0,0,1);
            }
        });

        song = (Button) findViewById(R.id.btnSong);
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null){
                    player.start();
                }
            }
        });

        videoView = (VideoView) findViewById(R.id.videoView);

        findViewById(R.id.btnMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.requestFocus();
                videoView.start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        player = MediaPlayer.create(this,R.raw.song1);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + File.separator +R.raw.movie));
        videoView.setMediaController(new MediaController(this));
        //在create已经存在prepare
//        try {
//            player.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null){
            player.release();
        }
    }
}
