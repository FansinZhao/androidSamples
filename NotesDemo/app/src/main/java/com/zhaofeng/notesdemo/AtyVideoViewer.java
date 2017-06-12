package com.zhaofeng.notesdemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import java.io.File;

public class AtyVideoViewer extends AppCompatActivity {

    private VideoView videoView;
    public final static String EXTRA_PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoView) findViewById(R.id.videoView);

        String path = getIntent().getStringExtra(EXTRA_PATH);
        if(path != null){
            videoView.setVideoURI(Uri.fromFile(new File(path)));
        }else{
            finish();
        }
    }
}
