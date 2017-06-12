package com.zhaofeng.filebrowser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<SDFile> adapter;
    private String dir = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<SDFile>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        dir = getIntent().getStringExtra("dir");
        if(dir == null || dir.length() == 0){
            dir = "/mnt/";
        }

        System.out.println("文件路径="+dir);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //user deal code
                System.out.println("通过权限");
                findFiles();
            }else {
                System.out.println("未通过权限");
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    System.out.println("获取权限!");
                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            666);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                    System.out.println("不提示弹出信息");
                }
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SDFile f = adapter.getItem(position);
                if(f.getFile().isDirectory()){
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    intent.putExtra("dir",f.getFile().getAbsolutePath());
                    startActivity(intent);
                }else {
                    if(f.getFile().getName().contains("png")){

                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromFile(f.getFile());
                        intent.setDataAndType(uri, "image/*");
                        startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 666: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    findFiles();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void findFiles(){
        File currFile = new File(dir);

        System.out.println(dir + "文件存在?"+currFile.exists());
        File[] files = currFile.listFiles();
        if (files == null || files.length == 0){
            System.out.println("不存在文件.....");
            return;
        }
        for (File file : files) {
            adapter.add(new SDFile(file));
        }
        System.out.println("onRequestPermissionsResult ...");
        // permission was granted, yay! Do the
        // contacts-related task you need to do.
    }
}
