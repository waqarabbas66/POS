package com.example.wb.my.Asynctasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.wb.my.Model.dbhelper;
import com.example.wb.my.Model.product;
import com.example.wb.my.item_list_page;
import com.example.wb.my.users_db_class;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class get_product_by_id_asynctask extends AsyncTask<String,Void,String> {
    StringBuffer buffer;
    List<product> productList;
    Context context;
    List<users_db_class> userinfo;
    public get_product_by_id_asynctask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String id=strings[0];
        buffer=new StringBuffer();
        try {
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/get_product_by_id_myshop.php");
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
                String info=URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
        userinfo=new Gson().fromJson(prefs.getString("user_info",""),new TypeToken<List<users_db_class>>(){}.getType());
        if(productList!=null&&productList.size()>0) {
            boolean inserted = new dbhelper(context).insert_product_toshoppingcart(productList.get(0).product_name, productList.get(0).product_price, productList.get(0).product_image, 1, userinfo.get(0).email,productList.get(0).product_weight, productList.get(0).id);
            if (inserted) {
                Toast.makeText(context, "Item Inserted", Toast.LENGTH_LONG).show();
            Cursor cursor= new dbhelper(context).select_product_by_name_and_weight(productList.get(0).product_name,productList.get(0).product_weight,String.valueOf(productList.get(0).product_price));
            if(cursor.getCount()>0){
                  new dbhelper(context).update_quantity(cursor.getString(1),cursor.getString(6),cursor.getInt(2),cursor.getInt(4));
            }
            Intent intent = new Intent(context, item_list_page.class);
            context.startActivity(intent);
            } else {
                Toast.makeText(context, "Item not Inserted", Toast.LENGTH_LONG).show();
            }
        }
    }
}
