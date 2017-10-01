package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class login extends AppCompatActivity implements View.OnClickListener {
    private
    SharedPreferences preferences;
    String mail;
    String pass;
    EditText input_email;
    EditText input_password ;
    Button btn_login ;
    String email1 ;
    String password1 ;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        input_email = (EditText) findViewById(R.id.input_email) ;
        input_password = (EditText) findViewById(R.id.input_password) ;
        //checking if email and passwords are empty

    }

    @Override
    public void onClick(View view) {
        mail = "a@a";
        pass = "123";
        email1 = input_email.getText().toString().trim();
        password1  = input_password.getText().toString().trim();

        if(TextUtils.isEmpty(email1)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password1)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mail.equals(email1))
        {
            if(pass.equals(password1))
            {
                Intent main = new Intent(login.this,MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mail" , email1);
                editor.putBoolean("logged", true);
                editor.commit();
                startActivity(main);
                finish();
                Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(this,"Wrong password",Toast.LENGTH_SHORT).show();
                input_password.getText().clear();
                return;
            }
        }
        else
        {
            Toast.makeText(this,"Email not found",Toast.LENGTH_LONG).show();
            input_email.getText().clear();
            input_password.getText().clear();

            return;
        }
    }
}