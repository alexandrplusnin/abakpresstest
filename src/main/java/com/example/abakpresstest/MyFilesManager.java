package com.example.abakpresstest;

import android.content.Context;

import java.io.File;

public class MyFilesManager {
    public static File getWorkingPath(Context context){
        if(context == null)
            return null;
        File tmp = context.getFilesDir();
        if(tmp!=null)
            return tmp.getParentFile();
        return null;
    }
}
