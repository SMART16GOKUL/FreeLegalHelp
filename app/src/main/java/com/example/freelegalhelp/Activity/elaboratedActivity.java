package com.example.freelegalhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.freelegalhelp.Activity.elaboratedprofile.AboutUs;
import com.example.freelegalhelp.Activity.elaboratedprofile.JoindWithUs;
import com.example.freelegalhelp.Activity.elaboratedprofile.Pmyprofile;
import com.example.freelegalhelp.Activity.elaboratedprofile.TermsAndCond;
import com.example.freelegalhelp.R;

public class elaboratedActivity extends AppCompatActivity {

    androidx.cardview.widget.CardView c1,c2,c3,c4,c5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_elaborated);

        c1  = findViewById(R.id.card1);
        c2  = findViewById(R.id.card2);
        c3  = findViewById(R.id.card3);
        c4  = findViewById(R.id.card4);
        c5  = findViewById(R.id.card5);


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =  new Intent(elaboratedActivity.this, Pmyprofile.class);
                startActivity(i);

            }
        });


        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =  new Intent(elaboratedActivity.this, AboutUs.class);
                startActivity(i);

            }
        });


        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =  new Intent(elaboratedActivity.this, TermsAndCond.class);
                startActivity(i);

            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                Intent i =  new Intent(elaborated.this,ShareApp.class);
//                startActivity(i);


                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "http://m8itsolutions.com/legalhelp/about-us.php";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });



        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =  new Intent(elaboratedActivity.this, JoindWithUs.class);
                startActivity(i);

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(elaboratedActivity.this,HomePageActivity.class);
        startActivity(i);
    }
}
