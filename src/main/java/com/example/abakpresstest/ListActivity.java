package com.example.abakpresstest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.abakpresstest.R;
import com.example.abakpresstest.models.DBHelper;
import com.example.abakpresstest.models.Product;
import com.example.abakpresstest.service.Response;
import com.example.abakpresstest.service.Service;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends ActionBarActivity {

    ProgressDialog progressDialog;
    private ArrayList<Product> products = new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if(MySharedPreferences.isDataDownloaded(this)){
            progressDialog = ProgressDialog.show(this, "Загрузка", "Пожалуйста, подождите. Идёт загрузка данных из локальной базы данных.");
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    products = DBHelper.getProducts(ListActivity.this);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();
                    ((ListView)findViewById(R.id.listView)).setAdapter(new ProductAdapter(ListActivity.this, products));
                    super.onPostExecute(aVoid);
                }
            }.execute();
        } else {
            progressDialog = ProgressDialog.show(this, "Загрузка", "Пожалуйста, подождите. Идёт загрузка данных из сети.");
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        products = new Response(Service.getData()).parse();
                        DBHelper.saveProducts(ListActivity.this, products);
                        MySharedPreferences.setDataDownloaded(ListActivity.this, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();
                    ((ListView)findViewById(R.id.listView)).setAdapter(new ProductAdapter(ListActivity.this, products));
                    super.onPostExecute(aVoid);
                }
            }.execute();
        }
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySharedPreferences.setDataDownloaded(ListActivity.this, false);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
