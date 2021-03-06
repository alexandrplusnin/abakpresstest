package com.example.abakpresstest.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Service {
    private static final String servicePath = "http://railsc.ru/resp.txt";

    public static String getData() throws IOException {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet(servicePath);

        HttpResponse response;

        response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        if (entity != null) {

            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);

            instream.close();
            return result;
        }

        return null;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
