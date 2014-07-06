package com.example.abakpresstest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ThumbTask extends AsyncTask<ImageViewHolder, Void, Bitmap> {

    protected ImageView imageView;
    @Override
    protected Bitmap doInBackground(ImageViewHolder... params) {
        imageView = params[0].imageView;
        URL url = null;
        try {
            Bitmap bmp = getImageBitmap(getFileName(params[0].context, params[0].url));
            if(bmp!=null)
                return bmp;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            url = new URL(params[0].url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection = null;
        try {
            if(url != null) {
                urlConnection = url.openConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(urlConnection!=null){
            Bitmap bmp = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(urlConnection.getInputStream(), null, options);
                if(options.outWidth>= 1024){
                    options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
                } else {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }
                try {
                    String filename = getFileName(params[0].context, params[0].url);
                    try {
                        FileOutputStream out = new FileOutputStream(filename);
                        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null) {
            if(imageView!=null)
                imageView.setImageBitmap(bitmap);
        }
    }

    private String getFileName(Context ctx, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        byte[] bytesOfMessage =  url.getBytes("UTF-8");
        md = MessageDigest.getInstance("MD5");
        return String.format("%s/files/%s.png", MyFilesManager.getWorkingPath(ctx), Base64.encodeToString(md.digest(bytesOfMessage), 0).replaceAll("\\W+", ""));
    }

    public Bitmap getImageBitmap(String fileName) {
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeFile(fileName);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        return bmp;
    }
}
