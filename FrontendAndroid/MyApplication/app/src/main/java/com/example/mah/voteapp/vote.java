package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static org.apache.http.protocol.HTTP.USER_AGENT;

public class vote extends AppCompatActivity implements View.OnClickListener {

    private
    Button vote;
    TextView result;
    Thread thread;
    RadioGroup  g;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences preferences;
    List<String> Answers = new ArrayList<String>();
    LinearLayout results,votes;
    List<String> AnswersID = new ArrayList<String>();
    List<String> AnswersCount = new ArrayList<String>();
    boolean test=true;
    int day , month , hour , minute , sec ;

    private static final String local = "http://10.42.0.1:8765/api/";
    String jsonObject;
    TextView cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        cd = (TextView) findViewById(R.id.cd);
        result = (TextView) findViewById(R.id.result);
        results = (LinearLayout) findViewById(R.id.results);
        votes = (LinearLayout) findViewById(R.id.votes);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int pos = preferences.getInt("pos", 314);
        String poll_name = preferences.getString("poll_name", "frr");
        final String poll_id = preferences.getString("poll_id", "ferfer");
        g = (RadioGroup) findViewById(R.id.Group);
        vote = (Button) findViewById(R.id.vote);
        vote.setOnClickListener(this);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {   runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // This code will always run on the UI thread, therefore is safe to modify UI elements.

                    if(0==1)
                    {
                        test=true;
                        votes.setVisibility(View.GONE);
                        results.setVisibility(View.VISIBLE);
                        return;

                    }
                    else
                    {
                        cd.setText(timeToStart());
                        test=false;
                    }

                }
            });

            }
        }, 0, 1000);//put here time 1000 milliseconds=1 second
        Log.e(TAG, "onCreate: "+test);

        if (test) {
            thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    try {
                        Answers.clear();
                        AnswersCount.clear();
                        String jsonObjectQ = getJSONObjectFromURL(local + "/getPollQuestions/" + poll_id);
                        JSONObject jsonnQ = new JSONObject(jsonObjectQ);
                        JSONObject jsonnQQ = jsonnQ.getJSONObject("data");
                        JSONArray questions = jsonnQQ.getJSONArray("questions");
                        String question_id = questions.getJSONObject(0).getString("id");


                        String jsonObjectA = getJSONObjectFromURL(local + "getQuestionChoices/" + question_id);
                        JSONObject jsonnA = new JSONObject(jsonObjectA);
                        JSONObject jsonnAA = jsonnA.getJSONObject("data");

                        JSONArray jsonarayA = jsonnAA.getJSONArray("offered_answers");
                        for (int i = 0; i < jsonarayA.length(); i++) {
                            JSONObject oneObjectA = jsonarayA.getJSONObject(i);
                            Answers.add(oneObjectA.getString("answer_text"));
                            AnswersCount.add(oneObjectA.getString("count"));


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
                String res = "";
                Log.e(TAG, "onCreate: zelgfiezhfo" );
                for (int i = 0; i < (Answers.toArray()).length; i++) {
                    res += (String) (Answers.toArray())[i] + ": " + (String) (AnswersCount.toArray())[i] + "\n";

                }
                result.setText("Result:\n\n " + res);



            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Answers.clear();
                    AnswersID.clear();
                    String jsonObjectQ = getJSONObjectFromURL(local+"/getPollQuestions/" + poll_id);
                    JSONObject jsonnQ = new JSONObject(jsonObjectQ);
                    JSONObject jsonnQQ = jsonnQ.getJSONObject("data");
                    JSONArray questions = jsonnQQ.getJSONArray("questions");
                    String question_id = questions.getJSONObject(0).getString("id");


                    String jsonObjectA = getJSONObjectFromURL(local+"getQuestionChoices/" + question_id);
                    JSONObject jsonnA = new JSONObject(jsonObjectA);
                    JSONObject jsonnAA = jsonnA.getJSONObject("data");
                    JSONObject jsonDate = jsonnAA.getJSONObject("poll");
                    String Start_Date = jsonDate.getString("start_date");
                    String End_Date = jsonDate.getString("end_date");
                    Start_Date = Start_Date.replaceAll("\\D+","");
                    int startDate =  Integer.parseInt(Start_Date)/1000000;
                    //Start_Date = String.valueOf(startDate);



                    Log.e(TAG, "run: " + startDate);
                    Log.e(TAG, "run: " + End_Date);

                    JSONArray jsonarayA = jsonnAA.getJSONArray("offered_answers");
                    for (int i = 0; i < jsonarayA.length(); i++) {
                        JSONObject oneObjectA = jsonarayA.getJSONObject(i);
                        Answers.add(oneObjectA.getString("answer_text"));
                        AnswersID.add(oneObjectA.getString("id"));



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
                j.setTextSize(20);
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

    public String getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String token = preferences.getString("token", "defaultStringIfNothingFound");

        // add request header
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", "Bearer "+ token );

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
            //Log.e("TAG", "onClick: ");
            int i = g.getCheckedRadioButtonId()-1;

            String id_vote = (String) (AnswersID.toArray())[i];
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("1", id_vote));
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    try{
                        jsonObject = getJSONObjectFromURL(local+"vote",nameValuePairs);
                        Log.e("TAG", "onClick: " + jsonObject );


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            vote.setEnabled(false);
            vote.setBackgroundColor(0);

            /*Intent success = new Intent(vote.this, MainActivity.class);
            startActivity(success);
            finish();*/
            Toast.makeText(this, "Vote Saved!", Toast.LENGTH_SHORT).show();


        }

    }
    public String getJSONObjectFromURL(String urlString, List nameValuePairs) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(urlString);

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

    public String timeToStart() {
         int SECONDS_IN_A_DAY = 24 * 60 * 60;
            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(new Date(0)); /* reset */
            thatDay.set(Calendar.DAY_OF_MONTH,30);
            thatDay.set(Calendar.MONTH,10); // 0-11 so 1 less
            thatDay.set(Calendar.YEAR, 2017);
            thatDay.set(Calendar.HOUR, 14);
            thatDay.set(Calendar.MINUTE, 37);

            Calendar today = Calendar.getInstance();
            long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
            long diffSec = diff / 1000;

            long days = diffSec / SECONDS_IN_A_DAY;
            long secondsDay = diffSec % SECONDS_IN_A_DAY;
            long seconds = secondsDay % 60;
            long minutes = (secondsDay / 60) % 60;
            long hours = (secondsDay / 3600); // % 24 not needed

            System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);
            String m = days+"d " + hours + "h " + minutes + "m " + seconds +"s";
            return m ;
        }
    public String timeToEnd() {
        int SECONDS_IN_A_DAY = 24 * 60 * 60;
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0)); /* reset */
        thatDay.set(Calendar.DAY_OF_MONTH,20);
        thatDay.set(Calendar.MONTH,10); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, 2017);

        Calendar today = Calendar.getInstance();
        long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long days = diffSec / SECONDS_IN_A_DAY;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);
        String m = days+"d " + hours + "h " + minutes + "m " + seconds +"s";
        return m ;
    }



}