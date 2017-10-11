package com.example.mah.voteapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mahmoud on 04/10/17.
 */

public class bureau extends Fragment {
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bureau,container,false);

        final TextView tv = (TextView) view.findViewById(R.id.tab2);
        new CountDownTimer(3000000000000000000L, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 3600;
                seconds = seconds % 60;
                tv.setText("Vote Starts In : " + String.format("%02d", hours) +String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tv.setText("Results");
            }
        }.start();

        return view;
    }

}
