package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
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

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.CoreProtocolPNames;

import static android.content.ContentValues.TAG;
import static org.apache.http.protocol.HTTP.USER_AGENT;


/**
 * Created by mahmoud on 04/10/17.
 */

public class decisions extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences preferences;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private Thread thread;
    List<String> Polls = new ArrayList<String>();
    List<String> PollsID = new ArrayList<String>();

    List<String> Questions = new ArrayList<String>();
    List<String> QuestionsID = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_card_view, container, false);
        preferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String jsonObject = getJSONObjectFromURL("http://10.0.2.2:8765/api/getPollsList");
                    JSONObject jsonn = new JSONObject(jsonObject);
                    JSONArray jsonaray= jsonn.getJSONArray("polls");
                    for(int i = 1 ; i<jsonaray.length(); i++ ) {
                        JSONObject oneObject = jsonaray.getJSONObject(i);

                        Polls.add(oneObject.getString("text"));
                        PollsID.add(oneObject.getString("id"));

                    }

                    } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();



        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext()));
            mRecyclerView.setHasFixedSize(true);

            try {
                mAdapter = new MyRecyclerViewAdapter(getDataSet());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mRecyclerView.setAdapter(mAdapter);
        } else {
            Log.e("Error", "Unable to find recyclerView");
        }
        return rootView;
    }


    public void onResume() {
        super.onResume();
        if (mRecyclerView != null) {

            ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                    .MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent vote1 = new Intent(getContext(), vote.class);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("poll_name", (String) (Polls.toArray())[position]);
                    editor.putInt("pos", position);
                    editor.putString("poll_id" , (String) (PollsID.toArray())[position]);
                    editor.commit();
                    startActivity(vote1);
                }
            });
        }
    }

    private ArrayList<DataObject> getDataSet() throws InterruptedException {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList results = new ArrayList<DataObject>();
        System.out.println(Polls);
        for (int i = 0 ; i < (Polls.toArray()).length ; i ++) {
            Log.e("a3", "getDataSet: " + (Polls.toArray())[i]);
            DataObject obj = new DataObject((String) (Polls.toArray())[i],"Description " + i);
            results.add(i, obj);
        }
        return results;
    }

    public static String getJSONObjectFromURL(String urlString) throws IOException, JSONException
    {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);

        // add request header
        request.addHeader("Accept","application/json");
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
