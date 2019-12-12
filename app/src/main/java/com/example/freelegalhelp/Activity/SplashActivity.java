package com.example.freelegalhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.freelegalhelp.R;
import com.example.freelegalhelp.loginsignup.LoginPage;

import pl.droidsonroids.gif.GifImageView;

import static com.example.freelegalhelp.loginsignup.LoginPage.MyPREFERENCES;

public class SplashActivity extends AppCompatActivity {

    GifImageView gifImageView;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        gifImageView = (GifImageView) findViewById(R.id.imageView);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                String name1 = sharedpreferences.getString("uemailKey",null);
                assert name1 != null;
                if(name1 != null){
                    Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }

                else {

                    Intent i = new Intent(SplashActivity.this, LoginPage.class);
                    startActivity(i);
                    finish();

                }

            }
        }, 5000);
    }
}

