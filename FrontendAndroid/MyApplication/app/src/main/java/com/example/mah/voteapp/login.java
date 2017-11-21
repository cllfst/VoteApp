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
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
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
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static org.apache.http.protocol.HTTP.USER_AGENT;


public class login extends AppCompatActivity implements View.OnClickListener {
    private final String TAG ="WAAAAAAAAAAAA";
    private static final String local = "http://10.42.0.1:8765/api/";

    private
    SharedPreferences preferences;

    EditText input_email;
    EditText input_password ;
    Button btn_login ;
    String email1 ;
    String password1 ;
    ScrollView myscrollview;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String jsonObject;


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
        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("email", email1));
        nameValuePairs.add(new BasicNameValuePair("password", password1));
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try{
                    jsonObject = getJSONObjectFromURL(local+"token",nameValuePairs);
                    Log.e("TAG", "onClick: " + jsonObject );
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
            JSONObject jsonObject1 = new JSONObject(jsonObject);
            if(jsonObject1.getLong("code") == -1 )
            {
                Toast.makeText(getApplicationContext(), "Email not found", Toast.LENGTH_LONG).show();
                input_email.getText().clear();
                input_password.getText().clear();

                return;
            }
            else if(jsonObject1.getLong("code") == -2 ) {
                Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                input_password.getText().clear();
                return;
            }
            else
            {
                Intent main = new Intent(login.this,MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mail" , email1);
                editor.putString("pass",password1);
                editor.putBoolean("logged", true);
                JSONObject data = jsonObject1.getJSONObject("data");
                String token = data.getString("token");
                editor.putString("token", token);
                editor.commit();
                startActivity(main);
                finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String getJSONObjectFromURL(String urlString , List nameValuePairs) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(urlString);

        // add request header
        //String msg = json.toString();

        request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF8"));

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