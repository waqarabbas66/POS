package com.example.wb.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wb.my.Model.dbhelper;
import com.example.wb.my.Model.product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class item_list_page extends AppCompatActivity {
    RecyclerView items_list;
    ArrayList<product> products;
    Button proceed;
    Button discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(item_list_page.this,Billing_Activity.class);
                startActivity(intent);
            }
        });
        discount=findViewById(R.id.discount);
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder discount_dialouge=new AlertDialog.Builder(item_list_page.this);
                discount_dialouge.setTitle("Give Discount");
                View discount_view= LayoutInflater.from(item_list_page.this).inflate(R.layout.discount_layout,null);
                final EditText discont_text=discount_view.findViewById(R.id.discount_rate);
                discount_dialouge.setPositiveButton("give discount", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(item_list_page.this,Billing_Activity.class);
                        i.putExtra("discount",discont_text.getText().toString());
                        startActivity(i);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setView(discount_view).show();
            }
        });

        items_list=findViewById(R.id.items_list);
        items_list.setLayoutManager(new LinearLayoutManager(this));
        products= new ArrayList<>();
        List<users_db_class> userinfo = new Gson().fromJson(prefs.getString("user_info", ""),new TypeToken<List<users_db_class>>(){}.getType());
        Cursor products_cursor = new dbhelper(this).get_products_in_cart(userinfo.get(0).email);
        if(products_cursor.getCount()== 0){
            Toast.makeText(this, "No Products", Toast.LENGTH_LONG).show();
        }else{
            while(products_cursor.moveToNext()){
                product p = new product ( products_cursor.getString(1), products_cursor.getString(3),products_cursor.getString(5),products_cursor.getInt(4), products_cursor.getInt(2), products_cursor.getInt(0));
                products.add(p);

            }
            items_list.setAdapter(new item_list_adapter(products, this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_item, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
