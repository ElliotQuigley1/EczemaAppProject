package com.example.calendarattempt4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

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
    private static ImageView imgview;
    private static ImageView imgview2;

    private static ImageView dry;
    private static ImageView dry_click;

    private static ImageView oozing;
    private static ImageView oozing_click;

    private static ImageView bleeding;
    private static ImageView bleeding_click;

    private static ImageView flaking;
    private static ImageView flaking_click;

    private static ImageView itchy;
    private static ImageView itchy_click;

    private int current_image;
    private int current_image_dry;
    private int current_image_oozing;
    private int current_image_bleeding;
    private int current_image_flaking;
    private int current_image_itchy;

    int[] images={R.drawable.cracking,R.drawable.cracking_clicked};
    int[] images_dry={R.drawable.dry,R.drawable.dry_clicked};
    int[] images_oozing={R.drawable.oozing,R.drawable.oozing_clicked};
    int[] images_bleeding={R.drawable.bleeding,R.drawable.bleeding_ticked};
    int[] images_flaking={R.drawable.flaking,R.drawable.flaking_clicked};
    int[] images_itchy={R.drawable.itchy,R.drawable.itchy_ticked};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_layout);
        buttonclick();
        buttonclick_dry();
        buttonclick_oozing();
        buttonclick_bleeding();
        buttonclick_flaking();
        buttonclick_itchy();

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
        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);

        startActivity(intent);
    }
    public void Logo(View view){
        Intent intent = new Intent(CalendarActivity.this, ActivityOptions.class);
        startActivity(intent);
    }

    public void buttonclick(){
        imgview=findViewById(R.id.imageView);
        imgview2=findViewById(R.id.imageView);
        imgview2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view){
                                            current_image++;
                                            current_image=current_image % images.length;
                                            imgview.setImageResource(images[current_image]);

                                        }
                                    }
        );
    }

    public void buttonclick_dry(){
        dry=findViewById(R.id.imageView_dry);
        dry_click=findViewById(R.id.imageView_dry);
        dry_click.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view){
                                             current_image_dry++;
                                             current_image_dry=current_image_dry % images_dry.length;
                                             dry.setImageResource(images_dry[current_image_dry]);

                                         }
                                     }
        );
    }

    public void buttonclick_oozing(){
        oozing=findViewById(R.id.imageView_oozing);
        oozing_click=findViewById(R.id.imageView_oozing);
        oozing_click.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view){
                                                current_image_oozing++;
                                                current_image_oozing=current_image_oozing % images_oozing.length;
                                                oozing.setImageResource(images_oozing[current_image_oozing]);

                                            }
                                        }
        );
    }

    public void buttonclick_bleeding(){
        bleeding=findViewById(R.id.imageView_bleeding);
        bleeding_click=findViewById(R.id.imageView_bleeding);
        bleeding_click.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view){
                                             current_image_bleeding++;
                                             current_image_bleeding=current_image_bleeding % images_bleeding.length;
                                             bleeding.setImageResource(images_bleeding[current_image_bleeding]);

                                         }
                                     }
        );
    }

    public void buttonclick_flaking(){
        flaking=findViewById(R.id.imageView_flaking);
        flaking_click=findViewById(R.id.imageView_flaking);
        flaking_click.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view){
                                                  current_image_flaking++;
                                                  current_image_flaking=current_image_flaking % images_flaking.length;
                                                  flaking.setImageResource(images_flaking[current_image_flaking]);

                                              }
                                          }
        );
    }

    public void buttonclick_itchy(){
        itchy=findViewById(R.id.imageView_itchy);
        itchy_click=findViewById(R.id.imageView_itchy);
        itchy_click.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view){
                                             current_image_itchy++;
                                             current_image_itchy=current_image_itchy % images_itchy.length;
                                             itchy.setImageResource(images_itchy[current_image_itchy]);

                                         }
                                     }
        );
    }
}
