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
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;
import static org.apache.http.protocol.HTTP.USER_AGENT;

public class settings extends AppCompatActivity implements View.OnClickListener {
    private
    SharedPreferences preferences;
    EditText oldpass;
    EditText newpass;
    Button settings;
    String oldpass1 , newpass1;
    String input_pass;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String jsonObject;
    String code;
    private static final String local = "http://10.42.0.1:8765/api/";


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
                    Toast.makeText(this, "New Password, too short!", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (oldpass1.equals(input_pass)) {

                        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("current_password", oldpass1));
                        nameValuePairs.add(new BasicNameValuePair("password", newpass1));

                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {

                                try{
                                    jsonObject = getJSONObjectFromURL(local+"updatePassword",nameValuePairs);
                                    JSONObject jsonn = new JSONObject(jsonObject);
                                    code = jsonn.getString("code");


                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                            if(code.equals("0"))
                            {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("pass" , newpass1);
                                Intent settings_main = new Intent(settings.this,MainActivity.class);
                                settings_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                settings_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(settings_main);
                                finish();
                                Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(this, "Current pass is wrong!!", Toast.LENGTH_LONG).show();
                        return;
                    }

                }
            }
        }

    public String getJSONObjectFromURL(String urlString, List nameValuePairs) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;

        HttpClient client = new DefaultHttpClient();
        HttpPut request = new HttpPut(urlString);

        // add request header
        //String msg = json.toString();

        request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF8"));
        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String token = preferences.getString("token", "defaultStringIfNothingFound");

        // add request header
        request.addHeader("Authorization", "Bearer "+ token );
        request.addHeader("Accept", "application/json");
        request.addHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");

        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);
        URL url = new URL(urlString);


        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return (result.toString());
    }
}




