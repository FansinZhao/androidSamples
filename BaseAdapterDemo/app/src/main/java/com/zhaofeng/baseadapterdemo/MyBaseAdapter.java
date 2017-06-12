package com.zhaofeng.baseadapterdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaofeng on 17-2-8.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private Context context;
    private int cellId = 0;

    private List<T> list = new ArrayList<>();

    public MyBaseAdapter(Context context,int resId) {
        this.context = context;
        this.cellId = resId;
    }


    public void add(T item){
        list.add(item);
        notifyDataSetChanged();
    }

    public void remove(int postiton){
        list.remove(postiton);
        notifyDataSetChanged();
    }

    public void removeLast(){
        remove(list.size() - 1);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(cellId,null);
        }

        initList(position,convertView,parent);

        return convertView;
    }

    protected abstract void initList(int position, View listView, ViewGroup parent);
}
