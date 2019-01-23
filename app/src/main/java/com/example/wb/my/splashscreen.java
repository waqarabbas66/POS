package com.example.wb.my;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

public class splashscreen extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        frameLayout = (FrameLayout) findViewById(R.id.myFramLay);
        animationDrawable = (AnimationDrawable)frameLayout.getBackground();

        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

}
