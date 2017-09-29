package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.example.mah.voteapp.login.MyPREFERENCES;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String mail = sharedpreferences.getString("mail",null);
                boolean verify = sharedpreferences.getBoolean("logged", false);


                if (verify) {
                    Log.d("!!!!!!!!!!!!!!", "sucess");
                    Log.d("!!!!!!!!!!!!!!1", String.valueOf(verify));
                    Intent main = new Intent(splashScreen.this,MainActivity.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                    finish();


                } else {
                    Log.d("!!!!!!!!!!!!!!", "signin");
                    startActivity(new Intent(splashScreen.this, login.class));
                    finish();
                }
            }
        }, 2000);
    }


}

