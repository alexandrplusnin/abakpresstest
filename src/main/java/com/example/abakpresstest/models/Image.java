package com.example.abakpresstest.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Image {

    public Image() {
    }

    public Image(int id, int parentId, int position, String pathThumb, String pathBig){
        this.id = id;
        this.parentId = parentId;
        this.position = position;
        this.pathThumb = pathThumb;
        this.pathBig = pathBig;
    }

    public static final String TABLE_NAME = "images";

    //table columns
    public static final String ID = "id";
    public static final String PARENT_ID = "parent_id";
    public static final String POSITION = "position";
    public static final String PATH_THUMB = "path_thumb";
    public static final String PATH_BIG = "path_big";

    //json properties
    public static final String ID_JSON = "id";
    public static final String POSITION_JSON = "position";
    public static final String PATH_THUMB_JSON = "path_thumb";
    public static final String PATH_BIG_JSON = "path_big";

    public static final String CREATION_STRING = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PARENT_ID + " INTEGER,"
            + POSITION + " INTEGER,"
            + PATH_THUMB + " TEXT,"
            + PATH_BIG + " TEXT"
            + ");";

    private int id;
    private int parentId;
    private int position;
    private String pathThumb;
    private String pathBig;

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public int getPosition() {
        return position;
    }

    public String getPathThumb() {
        return pathThumb;
    }

    public String getPathBig() {
        return pathBig;
    }

    public static Image parse(JSONObject item) throws JSONException {
        Image result = new Image();
        result.id = item.getInt(ID_JSON);
        result.position = item.getInt(POSITION_JSON);
        result.pathThumb = item.getString(PATH_THUMB_JSON);
        result.pathBig = item.getString(PATH_BIG_JSON);
        return result;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
