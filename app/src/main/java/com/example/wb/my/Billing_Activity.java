package com.example.wb.my;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.wb.my.Model.dbhelper;
import com.example.wb.my.Model.product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Billing_Activity extends AppCompatActivity {
    RecyclerView checkout_items_list;
android.support.v7.widget.Toolbar toolbar;
ArrayList<product> products;
String discount_rate;
int total_price_item;
    int discounted_rate;
int total_discount_price;
int total_price_after_discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        products = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        checkout_items_list = findViewById(R.id.checkout_items_list);
        checkout_items_list.setLayoutManager(new LinearLayoutManager(this));
        List<users_db_class> userinfo = new Gson().fromJson(prefs.getString("user_info", ""),new TypeToken<List<users_db_class>>(){}.getType());
        Cursor products_cursor = new dbhelper(this).get_products_in_cart(userinfo.get(0).email);
        if(products_cursor.getCount()== 0){
            Toast.makeText(this, "No Products", Toast.LENGTH_LONG).show();
        }else{
            while(products_cursor.moveToNext()){
                product p = new product ( products_cursor.getString(1), products_cursor.getString(3),products_cursor.getString(5),products_cursor.getInt(4), products_cursor.getInt(2), products_cursor.getInt(0));
                products.add(p);

            }
            checkout_items_list.setAdapter(new purchased_item_adapter(products, this));
        }
        discount_rate=getIntent().getStringExtra("discount");
         total_price_item=new dbhelper(Billing_Activity.this).getTotalOfAmount(userinfo.get(0).email);
         discounted_rate=(Integer.parseInt(discount_rate)*total_price_item)/100;
         total_discount_price=total_price_item-discounted_rate;
         total_price_after_discount=total_price_item-discounted_rate;
        Log.e("discount%",String.valueOf(discounted_rate));
        Log.e("total price",String.valueOf(total_discount_price));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

        }

        return super.onOptionsItemSelected(item);
    }
}
