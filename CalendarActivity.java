package com.example.calendarattempt4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private static ImageView medicine;
    private static ImageView medicine_click;

    private static ImageView ointment;
    private static ImageView ointment_click;

    private Button takephoto;

    private int current_image;
    private int current_image_dry;
    private int current_image_oozing;
    private int current_image_bleeding;
    private int current_image_flaking;
    private int current_image_itchy;
    private int current_image_medicine;
    private int current_image_ointment;
    private boolean toggle = false;
    private boolean toggle_dry = false;
    private boolean toggle_oozing = false;
    private boolean toggle_bleeding = false;
    private boolean toggle_flaking = false;
    private boolean toggle_itchy = false;
    private boolean toggle_medicine = false;
    private boolean toggle_ointment = false;

    int[] images={R.drawable.cracking,R.drawable.cracking_clicked};
    int[] images_dry={R.drawable.dry,R.drawable.dry_clicked};
    int[] images_oozing={R.drawable.oozing,R.drawable.oozing_clicked};
    int[] images_bleeding={R.drawable.bleeding,R.drawable.bleeding_ticked};
    int[] images_flaking={R.drawable.flaking,R.drawable.flaking_clicked};
    int[] images_itchy={R.drawable.itchy,R.drawable.itchy_ticked};
    int[] images_medicine={R.drawable.medicine,R.drawable.medicine_clicked};
    int[] images_ointment={R.drawable.ointment,R.drawable.ointment_clicked};

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
        buttonclick_medicine();
        buttonclick_ointment();
        takephoto = (Button) findViewById(R.id.takephoto);

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

    public void takephoto(View view) {
        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);

        startActivity(intent);
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
                                            //current_image++;
                                            //current_image=current_image % images.length;
                                            //imgview.setImageResource(images[current_image]);
                                            if (toggle == false) {
                                                toggle = true;
                                                imgview.setImageResource(R.drawable.cracking_clicked);
                                            } else if (toggle == true) {
                                                toggle = false;
                                                imgview.setImageResource(R.drawable.cracking);
                                            }
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
                                             //current_image++;
                                             //current_image=current_image % images.length;
                                             //imgview.setImageResource(images[current_image]);
                                             if (toggle_dry == false) {
                                                 toggle_dry = true;
                                                 dry_click.setImageResource(R.drawable.dry_clicked);
                                             } else if (toggle_dry == true) {
                                                 toggle_dry = false;
                                                 dry.setImageResource(R.drawable.dry);
                                             }
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
                                                //current_image++;
                                                //current_image=current_image % images.length;
                                                //imgview.setImageResource(images[current_image]);
                                                if (toggle_oozing == false) {
                                                    toggle_oozing = true;
                                                    oozing_click.setImageResource(R.drawable.oozing_clicked);
                                                } else if (toggle_oozing == true) {
                                                    toggle_oozing = false;
                                                    oozing.setImageResource(R.drawable.oozing);
                                                }
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
                                                  //current_image++;
                                                  //current_image=current_image % images.length;
                                                  //imgview.setImageResource(images[current_image]);
                                                  if (toggle_bleeding == false) {
                                                      toggle_bleeding = true;
                                                      bleeding_click.setImageResource(R.drawable.bleeding_ticked);
                                                  } else if (toggle_bleeding == true) {
                                                      toggle_bleeding = false;
                                                      bleeding.setImageResource(R.drawable.bleeding);
                                                  }
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
                                                 //current_image++;
                                                 //current_image=current_image % images.length;
                                                 //imgview.setImageResource(images[current_image]);
                                                 if (toggle_flaking == false) {
                                                     toggle_flaking = true;
                                                     flaking_click.setImageResource(R.drawable.flaking_clicked);
                                                 } else if (toggle_flaking == true) {
                                                     toggle_flaking = false;
                                                     flaking.setImageResource(R.drawable.flaking);
                                                 }
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
                                               //current_image++;
                                               //current_image=current_image % images.length;
                                               //imgview.setImageResource(images[current_image]);
                                               if (toggle_itchy == false) {
                                                   toggle_itchy = true;
                                                   itchy_click.setImageResource(R.drawable.itchy_ticked);
                                               } else if (toggle_itchy == true) {
                                                   toggle_itchy = false;
                                                   itchy.setImageResource(R.drawable.itchy);
                                               }
                                           }
                                       }
        );
    }

    public void buttonclick_medicine(){
        medicine=findViewById(R.id.imageView_medicine);
        medicine_click=findViewById(R.id.imageView_medicine);
        medicine_click.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view){
                                                  //current_image++;
                                                  //current_image=current_image % images.length;
                                                  //imgview.setImageResource(images[current_image]);
                                                  if (toggle_medicine == false) {
                                                      toggle_medicine = true;
                                                      medicine_click.setImageResource(R.drawable.medicine_clicked);
                                                  } else if (toggle_medicine == true) {
                                                      toggle_medicine = false;
                                                      medicine.setImageResource(R.drawable.medicine);
                                                  }
                                              }
                                          }
        );
    }

    public void buttonclick_ointment(){
        ointment=findViewById(R.id.imageView_ointment);
        ointment_click=findViewById(R.id.imageView_ointment);
        ointment_click.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view){
                                                  //current_image++;
                                                  //current_image=current_image % images.length;
                                                  //imgview.setImageResource(images[current_image]);
                                                  if (toggle_ointment == false) {
                                                      toggle_ointment = true;
                                                      ointment_click.setImageResource(R.drawable.ointment_clicked);
                                                  } else if (toggle_ointment) {
                                                      toggle_ointment = false;
                                                      ointment.setImageResource(R.drawable.ointment);
                                                  }
                                              }
                                          }
        );
    }
}
