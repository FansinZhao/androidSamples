package com.zhaofeng.sqllitedemo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private EditText name;

    private EditText age;

    private Button btnAdd;

    private ListView listView;

    private ArrayAdapter<User> adapter;

    private SQLiteDatabase writeDB;

    private SQLiteDatabase readDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB db = new DB(this);
        writeDB = db.getWritableDatabase();
        readDB = db.getReadableDatabase();

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<User>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        Cursor cursor =readDB.query("user",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            adapter.add(new User(name,age));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("name",name.getText().toString());
                cv.put("age",age.getText().toString());
                writeDB.insert("user",null,cv);
                adapter.add(new User(name.getText().toString(),age.getText().toString()));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this).setTitle("删除").setMessage("是否删除?").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = adapter.getItem(position);
                        writeDB.delete("user","name=? and age=?",new String[]{user.getName(),user.getAge()});
                        adapter.remove(user);
                    }
                }).setNegativeButton("否",null).show();

                return true;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("关闭sqlLiteDB!");
        readDB.close();
        writeDB.close();
    }
}
