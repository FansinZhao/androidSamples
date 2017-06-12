package com.zhaofeng.notesdemo;

/**
 * Created by zhaofeng on 17-2-16.
 */

public class CellListData {

    private int id = -1;
    private String path;
    private int iconId = R.drawable.video;

    public CellListData(String path) {
        this.path = path;
        if(path.endsWith("jpg")||path.endsWith("png")||path.endsWith("ico")){
            iconId = R.drawable.image;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
