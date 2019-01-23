package com.example.wb.my.Asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.wb.my.Model.catorgery;
import com.example.wb.my.catorgery_adapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class async_task extends AsyncTask{
    List<catorgery> catorgeries;
    Context context;
    StringBuffer buffer;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<String> spiner_category;


    public async_task(Spinner spinner, Context context) {
        this.spinner = spinner;
        this.context = context;
        spiner_category=new ArrayList<>();
    }

    public async_task(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        buffer = new StringBuffer();
        try {
            URL urls = new URL("https://helloworldshopingmall.000webhostapp.com/getCategories_myshop.php");
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String json;
            while ((json=reader.readLine())!=null){
                buffer.append(json);
            }
            catorgeries= new Gson().fromJson(buffer.toString(), new TypeToken<List<catorgery>>(){}.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(catorgeries!=null&&catorgeries.size()>0) {
        if(spinner!=null){
            for (int i=0; i<catorgeries.size(); i++){
                spiner_category.add(catorgeries.get(i).name);
            }
            Log.e("catorgery", buffer.toString());
            ArrayAdapter<String> adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,spiner_category);
            spinner.setAdapter(adapter);
        }else{


                Log.e("catorgery", buffer.toString());
                recyclerView.setAdapter(new catorgery_adapter(catorgeries, context));
            }
        }
    }
}

