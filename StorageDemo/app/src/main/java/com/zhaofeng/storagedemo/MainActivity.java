package com.zhaofeng.storagedemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private  Button btnSave;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.editText);
        readData();
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }

        });
    }

    private String readData(){
        try {
            InputStream reader = openFileInput("data");
            byte[] bytes = new byte[reader.available()];
            reader.read(bytes);
            reader.close();
            text.setText(new String(bytes,"utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void saveData(){
        try {
            OutputStream writer = openFileOutput("data", Context.MODE_PRIVATE);
            writer.write(text.getText().toString().getBytes("utf-8"));
            writer.flush();
            writer.close();
            Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
    }
}
