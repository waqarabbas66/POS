package com.example.wb.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.wb.my.Asynctasks.async_add_products;
import com.example.wb.my.Asynctasks.async_manually_add_product;
import com.example.wb.my.Asynctasks.async_task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class async_add_product extends AppCompatActivity {
  ImageView imageView;
  TextInputEditText name, price,quantity, weight;
  Spinner category_spinner;
  Button submit_btn;
  int PICK_IMAGE = 1;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView =(ImageView) findViewById(R.id.product_add_img);
        name = (TextInputEditText) findViewById(R.id.product_add_name);
        price = (TextInputEditText)findViewById(R.id.product_add_price);
        quantity = (TextInputEditText)findViewById(R.id.product_add_quantity);
        weight = (TextInputEditText)findViewById(R.id.product_add_weight);
        category_spinner = (Spinner)findViewById(R.id.select_catagory_spinner);
        submit_btn=(Button)findViewById(R.id.submit_button);
         new async_task(category_spinner, this).execute();
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] byteArrayImage = baos.toByteArray();
                String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                if( getIntent().getStringExtra("ScanCode")!=null) {
                    new async_add_products(async_add_product.this).execute(name.getText().toString(), price.getText().toString(), quantity.getText().toString(), weight.getText().toString(), category_spinner.getSelectedItem().toString(), encodedImage, getIntent().getStringExtra("ScanCode"));
                }else {
                    new async_manually_add_product(async_add_product.this).execute(name.getText().toString(), price.getText().toString(), quantity.getText().toString(), weight.getText().toString(), category_spinner.getSelectedItem().toString(), encodedImage);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
