package com.example.mah.voteapp;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
private Button logout ;
    SharedPreferences preferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String username = "Mahmoud";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setTitle("Hello, " + username);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);


    }





    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_icon:
                Intent logout_intent = new Intent(MainActivity.this,login.class);
                logout_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                logout_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("logged", false);
                editor.commit();
                startActivity(logout_intent);
                finish();
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                return true;

        }

        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
