package com.example.mah.voteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

public class settings extends AppCompatActivity implements View.OnClickListener {
    private
    EditText oldpass;
    EditText newpass;
    Button settings;
    String oldpass1 , newpass1;
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
    oldpass = (EditText)findViewById(R.id.oldPass);
    newpass = (EditText)findViewById(R.id.newPass);
    settings = (Button) findViewById(R.id.btn_settings);

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


            if (isEmpty(newpass1))
            {
                Toast.makeText(this, "Please insert your new pass", Toast.LENGTH_SHORT).show();
                return;
            }
            else
                {
                    if (newpass1.length() < 6)
                    {
                        Toast.makeText(this, "Too short!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(isEmpty(oldpass1))
                        {
                            Toast.makeText(this, "Please insert your current pass", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                                //if oldpass is not correct
                                Toast.makeText(this,"Wrong Password",Toast.LENGTH_SHORT).show();
                                return;
                        }
                        //Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();

                    }
                }

                }
           }




