package com.zhaofeng.filebrowser;

import java.io.File;

/**
 * Created by zhaofeng on 17-2-10.
 */

public class SDFile {

    private File file;

    public SDFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s",getFile().isDirectory()?"Dir":"File",getFile().getName());
    }


}
