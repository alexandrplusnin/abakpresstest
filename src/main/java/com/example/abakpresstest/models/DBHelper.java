package com.example.abakpresstest.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    private static final String TAG = "DBHelper";

    public static final String DATABASE_NAME = "AbakPressTest";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Product.CREATION_STRING);
        db.execSQL(Image.CREATION_STRING);
    }

    public static ArrayList<Product> getProducts(Context context){
        ArrayList<Product> result = new ArrayList<Product>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Product.TABLE_NAME, null);
        if(c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(Product.ID));
                result.add(new Product(id, c.getString(c.getColumnIndex(Product.NAME)),
                                       c.getInt(c.getColumnIndex(Product.IMAGES_CNT)), getImages(context, id)));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return result;
    }

    public static ArrayList<Image> getImages(Context context, int parentId){
        ArrayList<Image> result = new ArrayList<Image>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        Cursor c = db.query(Image.TABLE_NAME, null, String.format("%s='%s'", Image.PARENT_ID, parentId), null, null, null, null);
        if(c.moveToFirst()){
            do {
                result.add(new Image(c.getInt(c.getColumnIndex(Image.ID)),
                        c.getInt(c.getColumnIndex(Image.PARENT_ID)),
                        c.getInt(c.getColumnIndex(Image.POSITION)),
                        c.getString(c.getColumnIndex(Image.PATH_THUMB)),
                        c.getString(c.getColumnIndex(Image.PATH_BIG))));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return result;
    }

    public static void saveProducts(Context context, ArrayList<Product> products){
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(Product.TABLE_NAME, null, null);
        db.delete(Image.TABLE_NAME, null, null);
        ContentValues contentValues = new ContentValues();
        for(Product product : products){
            contentValues.clear();
            contentValues.put(Product.ID, product.getId());
            contentValues.put(Product.NAME, product.getName());
            contentValues.put(Product.IMAGES_CNT, product.getImagesCnt());
            db.insert(Product.TABLE_NAME, null, contentValues);
        }
        db.close();
        for(Product product : products){
            saveImages(context, product.getImages());
        }
    }

    public static void saveImages(Context context, ArrayList<Image> images){
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(Image image : images){
            contentValues.clear();
            contentValues.put(Image.ID, image.getId());
            contentValues.put(Image.PARENT_ID, image.getParentId());
            contentValues.put(Image.POSITION, image.getPosition());
            contentValues.put(Image.PATH_THUMB, image.getPathThumb());
            contentValues.put(Image.PATH_BIG, image.getPathBig());
            db.insert(Image.TABLE_NAME, null, contentValues);
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.w(TAG, "Upgrading database from version " + i + " to "
                + i2 + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+ Product.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Image.TABLE_NAME);
        onCreate(db);
    }
}
