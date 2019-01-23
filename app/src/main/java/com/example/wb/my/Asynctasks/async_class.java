package com.example.wb.my.Asynctasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.wb.my.Home;
import com.example.wb.my.users_db_class;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class async_class extends AsyncTask <String, Void, Void>{
    String Email, Password;
    List<users_db_class> users;
    Context context;
    StringBuffer buffer;
    public async_class(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Email =strings[0];
        Password = strings[1];
        buffer = new StringBuffer();
        try {
            URL urls = new URL("https://helloworldshopingmall.000webhostapp.com/login_myshop.php");
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setRequestMethod("POST");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String enc =  URLEncoder.encode("email", "UTF-8") + "=" +  URLEncoder.encode(Email, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" +  URLEncoder.encode(Password, "UTF-8");
            writer.write(enc);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String json;

            while ((json=reader.readLine())!=null){
                buffer.append(json);
            }
          users= new Gson().fromJson(buffer.toString(), new TypeToken<List<users_db_class>>(){}.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG ).show();
        Intent intent = new Intent(context,Home.class);
        Log.e("json",buffer.toString());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
           preferences.edit().putString("user_info", new Gson().toJson(users)).apply();
           context.startActivity(intent);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}
