package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class vote extends AppCompatActivity implements View.OnClickListener {
    private
    Button vote;
    TextView title;
    Thread thread;
    RadioGroup rprms, g;
    List<String> Questions = new ArrayList<String>();
    List<String> QuestionsID = new ArrayList<String>();
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences preferences;
    List<String> Answers = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int pos = preferences.getInt("pos", 314);
        String poll_name = preferences.getString("poll_name", "frr");
        final String poll_id = preferences.getString("poll_id", "ferfer");
        g = (RadioGroup) findViewById(R.id.Group);
        vote = (Button) findViewById(R.id.vote);
        vote.setOnClickListener(this);
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String jsonObjectQ = getJSONObjectFromURL("http://10.0.2.2:8765/api/getPollQuestions/" + poll_id);
                    JSONObject jsonnQ = new JSONObject(jsonObjectQ);
                    JSONObject jsonnQQ = jsonnQ.getJSONObject("poll");
                    JSONArray questions = jsonnQQ.getJSONArray("questions");
                    String question_id = questions.getJSONObject(0).getString("id");


                    String jsonObjectA = getJSONObjectFromURL("http://10.0.2.2:8765/api/getQuestionChoices/" + question_id);
                    JSONObject jsonnA = new JSONObject(jsonObjectA);
                    JSONObject jsonnAA = jsonnA.getJSONObject("question");

                    JSONArray jsonarayA = jsonnAA.getJSONArray("offered_answers");
                    for (int i = 0; i < jsonarayA.length(); i++) {
                        JSONObject oneObjectA = jsonarayA.getJSONObject(i);
                        Answers.add(oneObjectA.getString("answer_text"));

                    }
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
            for (int i = 0; i < (Answers.toArray()).length; i++) {
                RadioButton j = new RadioButton(this);
                j.setText((String) (Answers.toArray())[i]);
                g.addView(j);


            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //setetxt

        //onclick validate show toast

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        toolbar.setTitle(poll_name);
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

    public static String getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);

        // add request header
        request.addHeader("Accept", "application/json");
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

    @Override
    public void onClick(View view) {
        if (g.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "you have to check an option", Toast.LENGTH_SHORT).show();

        } else {
            Intent success = new Intent(vote.this, MainActivity.class);
            startActivity(success);
            finish();
            Toast.makeText(this, "Congrats!!", Toast.LENGTH_SHORT).show();

        }

    }
}