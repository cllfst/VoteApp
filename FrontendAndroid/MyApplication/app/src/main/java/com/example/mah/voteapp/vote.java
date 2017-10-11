package com.example.mah.voteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

public class vote extends AppCompatActivity {
    private
    RadioButton choice1;
    RadioButton choice2;
    TextView title;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int pos = preferences.getInt("pos",314);
        String poll_name = preferences.getString("poll_name","frr");

        choice1 = (RadioButton) findViewById(R.id.choice1);
        choice2 = (RadioButton) findViewById(R.id.choice2);
        //shared refs
        choice1.setText("Choice 1 of " + pos);
        choice2.setText("Choice 2 of " + pos);

        //setetxt

        //onclick validate show toast

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Vote for " + poll_name);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
