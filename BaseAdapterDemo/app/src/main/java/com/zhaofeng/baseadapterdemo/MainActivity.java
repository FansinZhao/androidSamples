package com.zhaofeng.baseadapterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;

    private Button add,remove;

    private MyBaseAdapter<String> adapter;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new MyBaseAdapter<String>(this, android.R.layout.simple_list_item_1) {
            @Override
            protected void initList(int position, View listView, ViewGroup parent) {
                ((TextView)(listView)).setText(getItem(position));
            }
        };
        listView.setAdapter(adapter);
        for (position = 1; position < 10; position++) {
            adapter.add("item"+position);
        }

        add = (Button) findViewById(R.id.addBtn);
        remove = (Button) findViewById(R.id.removeBtn);

        add.setOnClickListener(this);
        remove.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.addBtn:
                adapter.add("item"+position++);
                break;
            case R.id.removeBtn:
                adapter.removeLast();
                break;
        }

    }
}
