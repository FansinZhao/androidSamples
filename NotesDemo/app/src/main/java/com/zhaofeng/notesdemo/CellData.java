package com.zhaofeng.notesdemo;

/**
 * Created by zhaofeng on 17-2-15.
 */

public class CellData {

    private String name;
    private String content;
    private String date;

    public CellData(String name, String content, String date) {
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getName()+" "+getContent()+" "+getDate();
    }
}


