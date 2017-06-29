package com.zyyydqpi.dpc;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by strom on 2017/6/29.
 */

public class FileUntil {

    public static String get(Context context, String fileName) throws IOException {
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()){
            return null;
        }
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(file));
        String json = reader.readLine();
        reader.close();
        return json;
    }
    public static void save(Context context, String fileName, String json) throws IOException {
        File file = new File(context.getFilesDir(), fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
