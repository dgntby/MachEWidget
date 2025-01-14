package com.example.khughes.machewidget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

// Copied from https://mobikul.com/zip-unzip-file-folder-android-programmatically/

public class ZipManager {
    private static final String TAG = "ZipManager";
    private static final int BUFFER_SIZE = 6 * 1024;

    public static File zip(String[] files, String zipFile) throws IOException {
        BufferedInputStream origin;
        File fred = new File(zipFile);
        try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(fred)))) {
            try {
                byte[] data = new byte[BUFFER_SIZE];

                for (String file : files) {
                    FileInputStream fi = new FileInputStream(file);
                    origin = new BufferedInputStream(fi, BUFFER_SIZE);
                    try {
                        ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf("/") + 1));
                        out.putNextEntry(entry);
                        int count;
                        while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                            out.write(data, 0, count);
                        }
                    } finally {
                        origin.close();
                    }
                }
            } finally {
                out.close();
            }
        }
        return fred;
    }

    public static void unzip(Context context, Uri zipFile) throws IOException {
        try {
            File dataDir = context.getDataDir();
            File fromDir = new File(dataDir, "shared_prefs");

            ZipInputStream zin = new ZipInputStream(new FileInputStream(
                    context.getContentResolver().openFileDescriptor(zipFile, "r").getFileDescriptor()));
            try {
                ZipEntry ze;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = fromDir + File.separator + ze.getName();

                    if (ze.isDirectory()) {
                        File unzipFile = new File(path);
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        FileOutputStream fout = new FileOutputStream(path, false);

                        try {
                            for (int c = zin.read(); c != -1; c = zin.read()) {
                                fout.write(c);
                            }
                            zin.closeEntry();
                        } finally {
                            fout.close();
                        }
                    }
                }
            } finally {
                zin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Unzip exception", e);
        }
    }

    public static void zipStuff(Context context) {
        File dataDir = context.getDataDir();
        String zipfile = new File(dataDir, "prefs.zip").toString();

        // Get all the files we need to zip
        File fromDir;
        fromDir = new File(dataDir, "shared_prefs");
        ArrayList<String> fileList = new ArrayList<>();
        for (File from : fromDir.listFiles()) {
            fileList.add(from.toString());
        }
        String[] fromFiles = fileList.toArray(new String[0]);

        // Create the zip file
        File zipfileName;
        try {
            zipfileName = ZipManager.zip(fromFiles, zipfile);
        } catch (IOException e) {
            LogFile.e(context, MainActivity.CHANNEL_ID, "exception in ZipManager.zipStuff(): ", e);
            return;
        }

        InputStream inputStream ;
        try {
            inputStream = new FileInputStream(zipfileName);
        Uri fileCollection = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            fileCollection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, "prefs.zip");
        contentValues.put(MediaStore.Downloads.MIME_TYPE, "application/zip");
        ContentResolver resolver = context.getContentResolver();

        Uri uri = resolver.insert(fileCollection, contentValues);
        OutputStream outStream = resolver.openOutputStream(uri);
        int len;
        byte[] buffer = new byte[65536];
        while ((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inputStream.close();
        outStream.close();
        zipfileName.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}