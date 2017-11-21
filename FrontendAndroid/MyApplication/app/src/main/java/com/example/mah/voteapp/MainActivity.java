package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    private
    SharedPreferences preferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



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
                editor.putString("token", "");
                editor.commit();
                startActivity(logout_intent);
                finish();
                return true;
            case R.id.settings:
                Intent settings_intent = new Intent(MainActivity.this,settings.class);
                startActivity(settings_intent);

                return true;
        }

        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new decisions(), "Decisions");
        adapter.addFragment(new bureau(), "Elections");
        viewPager.setAdapter(adapter);
    }

}
