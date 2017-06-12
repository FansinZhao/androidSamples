package com.zhaofeng.sharepeferencedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private String KEY = "mykey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("mysp", Context.MODE_PRIVATE);

        final CheckBox ctv = (CheckBox) findViewById(R.id.checkBox);
        ctv.setChecked(sp.getBoolean(KEY,false));
        ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctv.isChecked()){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(KEY,true);
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.commit();
                }
            }
        });
        if(ctv.isChecked()){
            new AlertDialog.Builder(MainActivity.this).setTitle("欢迎").setMessage("你好,欢迎使用!").setPositiveButton("关闭",null).show();
        }
    }
}
