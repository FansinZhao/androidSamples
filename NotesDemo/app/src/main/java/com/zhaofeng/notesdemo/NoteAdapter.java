package com.zhaofeng.notesdemo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaofeng on 17-2-16.
 */

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<CellListData> list = new ArrayList<>();

    public NoteAdapter(Context context) {
        this.context = context;
    }

    public void add(String path){
        list.add(new CellListData(path));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CellListData getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            CellListData cellListData = list.get(position);
            if(cellListData.getPath().endsWith("png")||cellListData.getPath().endsWith("jpg")||cellListData.getPath().endsWith("ico")){
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_photo,null);
                ImageView view = (ImageView) convertView.findViewById(R.id.imageView);
                view.setImageURI(Uri.fromFile(new File(cellListData.getPath())));
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_video,null);
                VideoView view = (VideoView) convertView.findViewById(R.id.videoView);
                view.setVideoURI(Uri.fromFile(new File(cellListData.getPath())));
                view.setMediaController(new MediaController(context));
            }
        }

        return convertView;
    }
}
