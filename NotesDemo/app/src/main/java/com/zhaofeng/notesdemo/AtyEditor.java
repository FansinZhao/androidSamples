package com.zhaofeng.notesdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.zhaofeng.notesdemo.db.DB;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AtyEditor extends AppCompatActivity {

    public final static int REQUEST_NEW_NOTE = 1;
    public final static int REQUEST_EDIT_NOTE = 2;
    private static final int REQUEST_PHOTO_CODE = 3;
    private static final int REQUEST_VIDEO_CODE = 4;

    private Button btnSave,btnCancel,btnAddPhoto,btnAddVideo;

    private DB db;

    private SQLiteDatabase writeDb;

    private EditText etName,etContent;
    private String  filePath;

    private int nodeId;

    private NoteAdapter adapter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        db = new DB(this);
        writeDb = db.getWritableDatabase();

        etName = (EditText) findViewById(R.id.etName);
        etContent = (EditText) findViewById(R.id.etContent);


        nodeId = getIntent().getIntExtra(DB.COLUMN_ID,-1);
        if(nodeId > -1){
            etName.setText(getIntent().getStringExtra(DB.COLUMN_NOTE_NAME));
            etContent.setText(getIntent().getStringExtra(DB.COLUMN_NOTE_CONTENT));
        }

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(DB.COLUMN_NOTE_NAME,etName.getText().toString());
                cv.put(DB.COLUMN_NOTE_CONTENT,etContent.getText().toString());
                cv.put(DB.COLUMN_NOTE_DATE,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                int r = (int) writeDb.insert(DB.TABLE_NOTE,null,cv);
                if (r > -1){
                    Toast.makeText(AtyEditor.this,"保存成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AtyEditor.this,"保存失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AtyEditor.this,MainActivity.class);
                startActivity(i);
            }
        });

        btnAddPhoto = (Button) findViewById(R.id.btnAddPhoto);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+File.separator+System.currentTimeMillis()+".jpg";

                File currentImageFile = new File(filePath);

                if (!currentImageFile.exists()){
                    try {
                        currentImageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                startActivityForResult(i,REQUEST_PHOTO_CODE);
            }
        });

        btnAddVideo = (Button) findViewById(R.id.btnAddVideo);
        btnAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+File.separator+System.currentTimeMillis()+".mp4";

                File currentImageFile = new File(filePath);

                if (!currentImageFile.exists()){
                    try {
                        currentImageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                startActivityForResult(i,REQUEST_VIDEO_CODE);
            }
        });

        adapter = new NoteAdapter(this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AtyEditor.this,"显示多媒体文件",Toast.LENGTH_SHORT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_NEW_NOTE:

                    break;
                case REQUEST_EDIT_NOTE:
                    etName.setText(data.getStringExtra(DB.COLUMN_NOTE_NAME));
                    etContent.setText(data.getStringExtra(DB.COLUMN_NOTE_CONTENT));
                    //查询附件
                    break;
                case REQUEST_PHOTO_CODE:
//                    adapter.add(filePath);
//                    Intent i = new Intent(AtyEditor.this,AtyPhotoViewer.class);
//                    i.putExtra(AtyPhotoViewer.EXTRA_PATH,filePath);
//                    startActivity(i);
//                    break;
                case REQUEST_VIDEO_CODE:
                    adapter.add(filePath);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        writeDb.close();
        super.onDestroy();
    }
}
