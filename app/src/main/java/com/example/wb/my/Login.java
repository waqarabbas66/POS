package com.example.wb.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wb.my.Asynctasks.async_class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Login extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    Button login_btn;
    SharedPreferences preferences;
    FirebaseAuth firebaseAuth;
    TextView signup_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        preferences=PreferenceManager.getDefaultSharedPreferences(Login.this);
        setSupportActionBar(toolbar);
        login_btn = (Button) findViewById(R.id.sign_in);
        signup_txt = (TextView)findViewById(R.id.login_id);
        List<users_db_class> lists = new Gson().fromJson(preferences.getString("user_info",""),new TypeToken<List<users_db_class>>(){}.getType());

        if (lists!=null){
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        }

        signup_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class );
                startActivity(i);
                finish();
            }
        });

        username = (TextInputEditText)findViewById(R.id.email_txt);
        password = (TextInputEditText)findViewById(R.id.password_txt);
        firebaseAuth = (FirebaseAuth.getInstance());
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            new async_class(Login.this).execute(username.getText().toString(),password.getText().toString());
                        }else {
                            Toast.makeText(Login.this,"Please provide valid username and password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }

}
