package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

public class settings extends AppCompatActivity implements View.OnClickListener {
    private
    SharedPreferences preferences;
    EditText oldpass;
    EditText newpass;
    Button settings;
    String oldpass1 , newpass1;
    String input_pass;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    oldpass = (EditText)findViewById(R.id.oldPass);
    newpass = (EditText)findViewById(R.id.newPass);
    settings = (Button) findViewById(R.id.btn_settings);
    settings.setOnClickListener(this);
    input_pass = preferences.getString("pass","123");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

            oldpass1 = oldpass.getText().toString().trim();
            newpass1 = newpass.getText().toString().trim();

            if (TextUtils.isEmpty(oldpass1)) {
                Toast.makeText(this, "Please insert your current pass", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(newpass1)) {
                Toast.makeText(this, "Please insert your new pass", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (newpass1.length() < 6) {
                    Toast.makeText(this, "Too short!!", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (oldpass1.equals(input_pass)) {
                        Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();
                        Intent settings_main = new Intent(settings.this,MainActivity.class);
                        settings_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        settings_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(settings_main);
                        finish();

                    } else {
                        Toast.makeText(this, "Current pass is wrong!!", Toast.LENGTH_LONG).show();
                        return;
                    }

                }
            }
        }

}




