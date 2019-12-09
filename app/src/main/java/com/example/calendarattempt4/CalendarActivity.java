package com.example.calendarattempt4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ganesh on 6/10/2017.
 */

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private String date_chosen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_layout);
        mCalendarView = findViewById(R.id.smth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        date_chosen = sdf.format(new Date(mCalendarView.getDate()));
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = year + "/" + month + "/"+ dayOfMonth ;
                Log.d(TAG, "onSelectedDayChange: yyyy/MM/dd:" + date);
                date_chosen = date;

            }
        });
    }
    public void Go(View view) {
        Intent intent = new Intent(CalendarActivity.this, ActivityOptions.class);
        intent.putExtra("date",date_chosen);
        startActivity(intent);
    }
    public void Logo(View view){
        Intent intent = new Intent(CalendarActivity.this, ActivityOptions.class);
        startActivity(intent);
    }
}
