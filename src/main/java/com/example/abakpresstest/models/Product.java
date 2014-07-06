package com.example.abakpresstest.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product {

    public Product() {
    }

    public Product(int id, String name, int imagesCnt, ArrayList<Image> images){
        this.id = id;
        this.name = name;
        this.imagesCnt = imagesCnt;
        this.images = images;
    }

    public static final String TABLE_NAME = "products";

    //table columns
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IMAGES_CNT = "images_cnt";

    //json properties
    public static final String ID_JSON = "id";
    public static final String NAME_JSON = "name";
    public static final String IMAGES_CNT_JSON = "images_cnt";
    public static final String IMAGES_JSON = "images";

    public static final String CREATION_STRING = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME + " TEXT,"
            + IMAGES_CNT + " INTEGER"
            + ");";

    private int id;
    private String name;
    private int imagesCnt;
    private ArrayList<Image> images = new ArrayList<Image>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImagesCnt() {
        return imagesCnt;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public static Product parse(JSONObject item) throws JSONException {
        Product result = new Product();
        result.id = item.getInt(ID_JSON);
        result.name = item.getString(NAME_JSON);
        result.imagesCnt = item.getInt(IMAGES_CNT_JSON);
        JSONArray imagesJSON = item.getJSONArray(IMAGES_JSON);
        for(int i = 0; i < imagesJSON.length(); i++){
            JSONObject item2 = imagesJSON.getJSONObject(i);
            Image image = Image.parse(item2);
            image.setParentId(result.id);
            result.images.add(image);
        }
        return result;
    }
}

