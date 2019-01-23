package com.example.wb.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wb.my.Asynctasks.async_add_category;
import com.example.wb.my.Asynctasks.async_edit_product;
import com.example.wb.my.Asynctasks.async_task;
import com.example.wb.my.Asynctasks.get_product_by_id_asynctask;
import com.example.wb.my.Model.catorgery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
ArrayList<catorgery> catorgeries;
RecyclerView catorgery_list;
    SharedPreferences prefs;
    String nateja;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs=PreferenceManager.getDefaultSharedPreferences(Home.this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
          catorgery_list=findViewById(R.id.catorgery_list);
          catorgery_list.setLayoutManager(new LinearLayoutManager(this));
          new async_task(this,catorgery_list).execute();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = LayoutInflater.from(this).inflate(R.layout.nav_header_home,null);
        navigationView.addHeaderView(v);
        ImageView images = v.findViewById(R.id.image);
        TextView names = v.findViewById(R.id.name);
        TextView emails = v.findViewById(R.id.email);
        List<users_db_class> lists = new Gson().fromJson(prefs.getString("user_info",""),new TypeToken<List<users_db_class>>(){}.getType());
        names.setText(lists.get(0).name);
        emails.setText(lists.get(0).email);
        if(lists.get(0).user_role.equals("retailer")){
            navigationView.inflateMenu(R.menu.retailer_menu);
        }else if(lists.get(0).user_role.equals("Admin")){
            navigationView.inflateMenu(R.menu.activity_home_drawer);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(item.getItemId()== R.id.add_product){
            Intent intent = new Intent(Home.this, async_add_product.class);
            startActivity(intent);
        }
        else if(item.getItemId()== R.id.sign_out){
           prefs.edit().remove("user_info").apply();
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
            finish();
        }
        else if(item.getItemId()== R.id.add_catagory){
            Intent intent = new Intent(Home.this, add_category.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.add_by_barcode){
            i=0;
            new IntentIntegrator(Home.this).setCaptureActivity(ScannerActivity.class).initiateScan();
        }
        else if(item.getItemId()==R.id.items){
            Intent intent = new Intent(Home.this, item_list_page.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.invoice_making){
            i=1;
            new IntentIntegrator(Home.this).setCaptureActivity(ScannerActivity.class).initiateScan();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
                this.nateja = result.getContents();
                //howResultDialogue(result.getContents());
                if (i == 0) {
                    Intent intent = new Intent(Home.this, async_add_product.class);
                    intent.putExtra("ScanCode", nateja);
                    startActivity(intent);
                }
                    else if(i==1){
                        new get_product_by_id_asynctask(Home.this).execute(nateja);

                    // This is important, otherwise the result will not be passed to the fragment
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
   /* public void showResultDialogue(String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Add Product", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if(i==0){
                            Intent intent = new Intent(Home.this, async_add_product.class);
                            intent.putExtra("ScanCode", nateja);
                            startActivity(intent);
                        }else if(i==1){
                            new get_product_by_id_asynctask(Home.this).execute(nateja);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();
    }*/

        }
    }}