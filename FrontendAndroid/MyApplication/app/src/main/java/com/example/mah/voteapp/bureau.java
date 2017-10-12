package com.example.mah.voteapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mahmoud on 04/10/17.
 */

public class bureau extends Fragment {

    CountDownTimer mCountDownTimer;
    long mInitialTime = DateUtils.DAY_IN_MILLIS * 0 +
            DateUtils.HOUR_IN_MILLIS * 0 +
            DateUtils.MINUTE_IN_MILLIS * 0 +
            DateUtils.SECOND_IN_MILLIS * 5;
    TextView mTextView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bureau,container,false);

        mTextView = view.findViewById(R.id.tab2);

        mCountDownTimer = new CountDownTimer(mInitialTime, 800) {
            StringBuilder time = new StringBuilder();
            @Override
            public void onFinish() {
                mTextView.setText("it is Time to vote noob");
                //mTextView.setText("Times Up!");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                time.setLength(0);
                // Use days if appropriate
                if(millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
                    long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
                    if (count > 1)
                        time.append(count).append(" days ");
                    else
                        time.append(count).append(" day ");

                    millisUntilFinished %= DateUtils.DAY_IN_MILLIS;

                }

                time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));

                mTextView.setText(time.toString());



            }
        }.start();


        return view;
    }

}
