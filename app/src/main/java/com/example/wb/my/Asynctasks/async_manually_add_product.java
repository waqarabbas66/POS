package com.example.wb.my.Asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class async_manually_add_product extends AsyncTask<String,Void,String> {
    StringBuffer buffer;
    Context context;
    HttpURLConnection connection;
    String name, catagory, quantity, price, weight, image;

    public async_manually_add_product(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {

        name=strings[0];
        price=strings[1];
        quantity=strings[2];
        weight=strings[3];
        catagory=strings[4];
        image=strings[5];
        buffer = new StringBuffer();
        try {

            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/myshop_products.php");
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            String enc =  URLEncoder.encode("Name", "UTF-8") + "=" +  URLEncoder.encode(name, "UTF-8") + "&" + URLEncoder.encode("Catagory", "UTF-8") + "=" +  URLEncoder.encode(catagory, "UTF-8") + "&" + URLEncoder.encode("Quantity", "UTF-8") + "=" +  URLEncoder.encode(quantity, "UTF-8")+ "&" + URLEncoder.encode("Price", "UTF-8") + "=" +  URLEncoder.encode(price, "UTF-8")+ "&" + URLEncoder.encode("weight", "UTF-8") + "=" +  URLEncoder.encode(weight, "UTF-8")+ "&" + URLEncoder.encode("Image", "UTF-8") + "=" +  URLEncoder.encode(image, "UTF-8" );
            writer.write(enc);
            writer.flush();
            BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while((json=reader.readLine())!=null){
                buffer.append(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
