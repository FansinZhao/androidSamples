package com.zhaofeng.camerademo;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class AtySysCamera extends AppCompatActivity {

    private static final int REQUEST_CODE_TAKE_PICTURE = 1;
    private Button btnSysStart;
    private ImageView imageView;
    private File currentImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_camera);

        btnSysStart = (Button) findViewById(R.id.btnSysStart);
        btnSysStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File dir = new File("/data/Images");
                if(!dir.exists()){
                    dir.mkdirs();
                }

                currentImageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), System.currentTimeMillis()+".jpg");

                if (!currentImageFile.exists()){
                    try {
                        currentImageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));

                startActivityForResult(intent,REQUEST_CODE_TAKE_PICTURE);
            }
        });
        imageView = (ImageView) findViewById(R.id.imageview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_TAKE_PICTURE:
                imageView.setImageURI(Uri.fromFile(currentImageFile));
                System.out.println(currentImageFile.exists()+"?????");
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
