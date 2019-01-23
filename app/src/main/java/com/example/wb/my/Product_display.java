package com.example.wb.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.wb.my.Asynctasks.get_product_by_catorgery;

public class Product_display extends AppCompatActivity {
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Catagory"));
        recyclerView = (RecyclerView)findViewById(R.id.product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(Product_display.this));
        new get_product_by_catorgery(Product_display.this, recyclerView).execute(getIntent().getStringExtra("Catagory"));
    }

}
