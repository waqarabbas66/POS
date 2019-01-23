package com.example.wb.my;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.wb.my.Asynctasks.async_edit_product;
import com.example.wb.my.Model.product;
import com.google.gson.Gson;

public class Edit_Product_detail extends AppCompatActivity {
    TextInputEditText productID;
    TextInputEditText productName;
    TextInputEditText productWeight;
    TextInputEditText productQuantity;
    TextInputEditText productPrice;
    Button button;
    product productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productID =(TextInputEditText) findViewById(R.id.id_txt);
        productName = (TextInputEditText) findViewById(R.id.name_txt);
        productWeight = (TextInputEditText)findViewById(R.id.weight_txt);
        productQuantity = (TextInputEditText)findViewById(R.id.quantity_txt);
        productPrice = (TextInputEditText) findViewById(R.id.price_txt);
        button = findViewById(R.id.submit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new async_edit_product(Edit_Product_detail.this).execute(productName.getText().toString(), productPrice.getText().toString(), productQuantity.getText().toString(),productWeight.getText().toString(), productID.getText().toString());
            }
        });
        productList=new Gson().fromJson(getIntent().getStringExtra("Products"),product.class);
        productID.setText(String.valueOf(productList.product_ID));
        productName.setText(productList.product_name);
        productWeight.setText(productList.product_weight);
        productQuantity.setText(String.valueOf(productList.product_quantity));
        productPrice.setText(String.valueOf(productList.product_price));

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
