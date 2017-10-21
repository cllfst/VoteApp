package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.data;


public class login extends AppCompatActivity implements View.OnClickListener {
    private final String TAG ="WAAAAAAAAAAAA";
    private
    SharedPreferences preferences;
    String mail;
    String pass;
    EditText input_email;
    EditText input_password ;
    Button btn_login ;
    String email1 ;
    String password1 ;
    ScrollView myscrollview;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private JSONObject json ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        input_email = (EditText) findViewById(R.id.input_email) ;
        input_password = (EditText) findViewById(R.id.input_password) ;
        myscrollview = (ScrollView) findViewById(R.id.myscroll);

        input_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myscrollview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild = myscrollview.getChildAt(myscrollview.getChildCount() - 1);
                        int bottom = lastChild.getBottom() + myscrollview.getPaddingBottom();
                        int sy = myscrollview.getScrollY();
                        int sh = myscrollview.getHeight();
                        int delta = bottom - (sy + sh);
                        myscrollview.smoothScrollBy(0, delta);
                    }
                }, 0);
                return false;
            }

        });

    }

    @Override
    public void onClick(View view) {
        mail = "admin@admin";
        pass = "admin";
        email1 = input_email.getText().toString().trim();
        password1  = input_password.getText().toString().trim();

        json = new JSONObject();
        try {
            json.put("email", email1);
            json.put("password", password1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    Log.e("JSOOOOOOOOOOOON", "onClick: " + json.toString());

                    String jsonObject = getJSONObjectFromURL("http://10.0.2.2:8765/api/login",json);
                    Log.e("TAG", "onClick: " + jsonObject );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();




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
                editor.putString("pass",password1);
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


    public static String getJSONObjectFromURL(String urlString , JSONObject json) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("accept", "application/json");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();


        //Write
        OutputStream outputStream = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(String.valueOf(json));
        writer.close();
        outputStream.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return (jsonString);
    }
}