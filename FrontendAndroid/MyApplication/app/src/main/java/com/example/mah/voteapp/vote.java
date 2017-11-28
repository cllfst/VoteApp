package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
    String code;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences preferences;
    List<String> Answers = new ArrayList<String>();
    LinearLayout results,votes;
    List<String> AnswersID = new ArrayList<String>();
    List<String> AnswersCount = new ArrayList<String>();
    String years = "";
    String days = "";
    String months = "";
    String hours ="";
    String minutes ="";
    String yeare = "";
    String daye = "";
    String monthe = "";
    String houre ="";
    String minutee ="";
    int yearis ,dayis , monthis , houris , minuteis  ;
    int yearie ,dayie , monthie , hourie , minuteie  ;

    private static final String local = "http://10.42.0.1:8765/api/";
    String jsonObject;
    TextView cd,starting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        cd = (TextView) findViewById(R.id.cd);
        result = (TextView) findViewById(R.id.result);
        results = (LinearLayout) findViewById(R.id.results);
        votes = (LinearLayout) findViewById(R.id.votes);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String poll_name = preferences.getString("poll_name", "frr");
        final String poll_id = preferences.getString("poll_id", "ferfer");
        g = (RadioGroup) findViewById(R.id.Group);
        vote = (Button) findViewById(R.id.vote);
        starting = (TextView)findViewById(R.id.starting);
        vote.setOnClickListener(this);
        final Timer t1 = new Timer();
        final Timer t2 = new Timer();


        t1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {   runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String s1 = timeToStart(yearis,monthis,dayis,houris,minuteis);
                    String s = s1.replaceAll("\\D+","");

                    // This code will always run on the UI thread, therefore is safe to modify UI elements.
                    if((s.equals("0000"))||(s1.contains("-")))
                    {
                        votes.setVisibility(View.VISIBLE);
                        starting.setVisibility(View.GONE);
                        results.setVisibility(View.GONE);
                        t1.cancel();
                        t1.purge();


                        t2.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {   runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String s2 = timeToEnd(yearie,monthie,dayie,hourie,minuteie);
                                    String s0 = s2.replaceAll("\\D+","");
                                    if((s0.equals("0000"))||(s2.contains("-")))
                                    {
                                        votes.setVisibility(View.GONE);
                                        starting.setVisibility(View.GONE);
                                        results.setVisibility(View.VISIBLE);
                                        t2.cancel();
                                        t2.purge();
                                        //results
                                        thread = new Thread(new Runnable() {

                                            @Override
                                            public void run() {

                                                try {

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
                                        //end of results
                                    }
                                    else
                                    {
                                        cd.setText("Vote will end in\n"+timeToEnd(yearie,monthie,dayie,hourie,minuteie));
                                    }

                                }
                            });

                            }
                        }, 0, 1000);


                    }


                    else
                    {
                        votes.setVisibility(View.GONE);
                        results.setVisibility(View.GONE);
                        starting.setText("Vote will start in\n"+timeToStart(yearis,monthis,dayis,houris,minuteis));

                    }

                }
            });

            }
        }, 0, 1000);//put here time 1000 milliseconds=1 second


        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

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
                    End_Date = End_Date.replaceAll("\\D+","");
                    String[] StartArray = Start_Date.split("");
                    String[] EndArray = End_Date.split("");



                    for(int i = 1 ; i < StartArray.length-6 ; i++)
                    {
                        if((i>=1) && (i<5))
                        {
                            years += StartArray[i];
                            yeare += EndArray[i];

                        }
                        if((i>=5) && (i<7))
                        {
                            months += StartArray[i];
                            monthe += EndArray[i];

                        }
                        if((i>=7) && (i<9))
                        {
                            days += StartArray[i];
                            daye += EndArray[i];

                        }
                        if((i>=9) && (i<11))
                        {
                            hours += StartArray[i];
                            houre += EndArray[i];

                        }
                        if((i>=11) && (i<13))
                        {
                            minutes += StartArray[i];
                            minutee += EndArray[i];

                        }


                    }

                    yearis = Integer.parseInt(years);
                    monthis = Integer.parseInt(months);
                    dayis = Integer.parseInt(days);
                    houris = Integer.parseInt(hours);
                    minuteis = Integer.parseInt(minutes);
                    yearie = Integer.parseInt(yeare);
                    monthie = Integer.parseInt(monthe);
                    dayie = Integer.parseInt(daye);
                    hourie = Integer.parseInt(houre);
                    minuteie = Integer.parseInt(minutee);


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
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String didVote = getJSONObjectFromURL(local+"/didVote/" + poll_id);
                    JSONObject didVoteObject = new JSONObject(didVote);

                    JSONObject response = didVoteObject.getJSONObject("response");
                    code = response.getString("code");
                    Log.e(TAG, "CODE: " + code );

                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
            if(code.equals("-1"))
            {
                vote.setEnabled(false);
                vote.setBackgroundColor(Color.WHITE);
                vote.setTextColor(Color.BLACK);
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
            Log.e(TAG, "onClick: " + i);

            String id_vote = (String) (AnswersID.toArray())[i];
            Log.e(TAG, "onClick: " + id_vote);
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("1", id_vote));
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    try{
                        jsonObject = getJSONObjectFromURL(local+"vote",nameValuePairs);
                        //Log.e("TAG", "onClick: " + jsonObject );


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();


            Intent success = new Intent(vote.this, MainActivity.class);
            startActivity(success);
            finish();
            Toast.makeText(this, "Vote Saved!", Toast.LENGTH_SHORT).show();


        }

    }
    public String getJSONObjectFromURL(String urlString, List nameValuePairs) throws IOException, JSONException {

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

    public String timeToStart(int yeari , int monthi ,int dayi, int houri , int minutei) {
         int SECONDS_IN_A_DAY = 24 * 60 * 60;
            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(new Date(0)); /* reset */
            thatDay.set(Calendar.DAY_OF_MONTH,dayi);
            thatDay.set(Calendar.MONTH,monthi-1); // 0-11 so 1 less
            thatDay.set(Calendar.YEAR, yeari);
            thatDay.set(Calendar.HOUR, houri);
            thatDay.set(Calendar.MINUTE, minutei);
        thatDay.set(Calendar.SECOND,0);

            Calendar today = Calendar.getInstance();
            long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
            long diffSec = diff / 1000;

            long days = diffSec / SECONDS_IN_A_DAY;
            long secondsDay = diffSec % SECONDS_IN_A_DAY;
            long seconds = secondsDay % 60;
            long minutes = (secondsDay / 60) % 60;
            long hours = (secondsDay / 3600); // % 24 not needed

            //System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);
            String m = days+"d " + hours + "h " + minutes + "m " + seconds +"s";
            return m ;
        }
    public String timeToEnd(int yeari , int monthi ,int dayi, int houri , int minutei) {
        int SECONDS_IN_A_DAY = 24 * 60 * 60;
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0)); /* reset */
        thatDay.set(Calendar.DAY_OF_MONTH,dayi);
        thatDay.set(Calendar.MONTH,monthi-1); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, yeari);
        thatDay.set(Calendar.HOUR, houri);
        thatDay.set(Calendar.MINUTE, minutei);
        thatDay.set(Calendar.SECOND,0);

        Calendar today = Calendar.getInstance();
        long diff =  thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long days = diffSec / SECONDS_IN_A_DAY;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        //System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);
        String m = days+"d " + hours + "h " + minutes + "m " + seconds +"s";
        return m ;
    }


}