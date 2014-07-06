package com.example.abakpresstest.service;

import com.example.abakpresstest.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Response {

    private String body;

    public Response(String body){
        this.body = body;
    }

    public ArrayList<Product> parse() throws JSONException {
        ArrayList<Product> result = new ArrayList<Product>();
        JSONArray products = new JSONObject(body).getJSONObject("content").getJSONArray("products");
        for(int i = 0; i < products.length(); i++){
            JSONObject item = products.getJSONObject(i);
            result.add(Product.parse(item));
        }
        return result;
    }

}
