package com.zhaofeng.notesdemo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class AtyPhotoViewer extends AppCompatActivity {

    private ImageView imageView;

    public final static String EXTRA_PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageView = (ImageView) findViewById(R.id.imageView);

        String path = getIntent().getStringExtra(EXTRA_PATH);

        if (path != null){
            imageView.setImageURI(Uri.fromFile(new File(path)));
        }else{
            finish();
        }
    }
}
