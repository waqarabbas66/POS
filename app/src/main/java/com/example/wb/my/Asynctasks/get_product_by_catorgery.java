package com.example.wb.my.Asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Spinner;

import com.example.wb.my.Model.product;
import com.example.wb.my.product_adapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class get_product_by_catorgery extends AsyncTask<String,Void,Void> {
List<product> productList;
Context context;
StringBuffer buffer;
RecyclerView product_list;

    public get_product_by_catorgery(Context context, RecyclerView product_list) {
        this.context = context;
        this.product_list = product_list;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String catorgery=strings[0];
        buffer=new StringBuffer();
        try {
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/product_catagory_myshop.php");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            String info=URLEncoder.encode("Category","UTF-8")+"="+URLEncoder.encode(catorgery,"UTF-8");
            writer.write(info);
            writer.flush();
            BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while((json=reader.readLine())!=null){
                buffer.append(json);
            }
            productList=new Gson().fromJson(buffer.toString(), new TypeToken<List<product>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e("product",buffer.toString());
        product_list.setAdapter(new product_adapter(productList,context));


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
