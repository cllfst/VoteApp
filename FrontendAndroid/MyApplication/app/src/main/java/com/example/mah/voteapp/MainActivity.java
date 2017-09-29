package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button logout ;
    SharedPreferences preferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent logout_intent = new Intent(MainActivity.this,login.class);
        logout_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logout_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("logged", false);
        editor.commit();
        startActivity(logout_intent);
        finish();
    }
}
