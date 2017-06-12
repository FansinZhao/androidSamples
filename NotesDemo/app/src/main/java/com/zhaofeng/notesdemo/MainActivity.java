package com.zhaofeng.notesdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zhaofeng.notesdemo.db.DB;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private static final int REQUEST_ADD_NOTE = 1;
    private static final int REQUEST_EDIT_NOTE = 2;
    private Button btnAddNote;
    private ListView listView;

    private ArrayAdapter<CellData> adapter;


    private DB db;

    private SQLiteDatabase readDb;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddNote = (Button) findViewById(R.id.btnAddNote);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AtyEditor.class);
                startActivityForResult(i,AtyEditor.REQUEST_NEW_NOTE);
            }
        });

        db = new DB(this);

        readDb = db.getReadableDatabase();

        listView = (ListView) findViewById(R.id.dataList);
        adapter = new ArrayAdapter<CellData>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);
        refresh();
    }

    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }

    //new String[]{DB.COLUMN_NOTE_NAME,DB.COLUMN_NOTE_CONTENT,DB.COLUMN_NOTE_DATE}
    private void refresh(){
        adapter.clear();
        cursor = readDb.query(DB.TABLE_NOTE,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            adapter.add(new CellData(cursor.getString(cursor.getColumnIndex(DB.COLUMN_NOTE_NAME)),cursor.getString(cursor.getColumnIndex(DB.COLUMN_NOTE_CONTENT)),cursor.getString(cursor.getColumnIndex(DB.COLUMN_NOTE_DATE))));
        }
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        readDb.close();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_ADD_NOTE:
                case REQUEST_EDIT_NOTE:
                    refresh();
                    break;
            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        cursor.moveToPosition(position);
        if(view != null){
            new AlertDialog.Builder(MainActivity.this).setTitle("是否删除").setMessage("是否删除?").setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    int r = readDb.delete(DB.TABLE_NOTE,"_id=?",new String[]{cursor.getInt(cursor.getColumnIndex(DB.COLUMN_ID))+""});
                    if(r > -1){
                        Toast.makeText(MainActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                        refresh();
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(MainActivity.this,"删除失败!",Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("否",null).show();
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        cursor.moveToPosition(position);

        Intent i = new Intent(MainActivity.this,AtyEditor.class);
        i.putExtra(DB.COLUMN_ID,cursor.getInt(cursor.getColumnIndex(DB.COLUMN_ID)));
        i.putExtra(DB.COLUMN_NOTE_NAME,cursor.getString(cursor.getColumnIndex(DB.COLUMN_NOTE_NAME)));
        i.putExtra(DB.COLUMN_NOTE_CONTENT,cursor.getString(cursor.getColumnIndex(DB.COLUMN_NOTE_CONTENT)));
        startActivityForResult(i,AtyEditor.REQUEST_EDIT_NOTE);
    }
}
