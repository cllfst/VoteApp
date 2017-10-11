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

import java.util.ArrayList;

/**
 * Created by mahmoud on 04/10/17.
 */

public class decisions extends Fragment {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences preferences;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private String[] Polls = {"JLL Date","KEY Date","Kool Party Winners"};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_card_view,container,false);
        preferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext()));
            mRecyclerView.setHasFixedSize(true);

            mAdapter = new MyRecyclerViewAdapter(getDataSet());

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
                    Log.i(LOG_TAG, " Clicked on Item " + Polls[position]);
                    Intent vote1 = new Intent(getContext(),vote.class);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("poll_name" , Polls[position]);
                    editor.putInt("pos",position);
                    editor.commit();
                    startActivity(vote1);
                }
            });
        }
    }
    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();

        for(int i=0 ; i < Polls.length ; i++) {
            Log.i("a3", "getDataSet: " + Polls[i]);

            DataObject obj = new DataObject(Polls[i],
                    "Description " + i);
            results.add(i, obj);
        }
        return results;
    }

}
