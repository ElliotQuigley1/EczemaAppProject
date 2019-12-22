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

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import static com.example.calendarattempt4.MainActivity.checkDay_on_state_change;

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

    private boolean toggle = true;
    private boolean toggle_dry = false;
    private boolean toggle_oozing = false;
    private boolean toggle_bleeding = false;
    private boolean toggle_flaking = false;
    private boolean toggle_itchy = false;
    private boolean toggle_medicine = false;
    private boolean toggle_ointment = false;
    private boolean togglers[] = {false,false,false,false,false,false,false,false};

    int[] images={R.drawable.cracking,R.drawable.cracking_clicked};
    int[] images_dry={R.drawable.dry,R.drawable.dry_clicked};
    int[] images_oozing={R.drawable.oozing,R.drawable.oozing_clicked};
    int[] images_bleeding={R.drawable.bleeding,R.drawable.bleeding_ticked};
    int[] images_flaking={R.drawable.flaking,R.drawable.flaking_clicked};
    int[] images_itchy={R.drawable.itchy,R.drawable.itchy_ticked};
    int[] images_medicine={R.drawable.medicine,R.drawable.medicine_clicked};
    int[] images_ointment={R.drawable.ointment,R.drawable.ointment_clicked};

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_layout);
        this.imageView = (ImageView)this.findViewById(R.id.imageView2);
        Button photoButton = (Button) this.findViewById(R.id.takephoto);
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

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
                try {
                    if (checkDay_on_state_change(date_chosen)) {
                        String downloaded_ans = MainActivity.D.answers;
                        char[] ans_char = new char[downloaded_ans.length()];
                        for (int i = 0; i<downloaded_ans.length(); i++) {
                            ans_char[i] = downloaded_ans.charAt(i);
                            if(downloaded_ans.charAt(i) == '1') {
                                togglers[i] = true;
                            } else if (downloaded_ans.charAt(i) == '0') {
                                togglers[i] = false;
                            }
                        }
                    } else {
                        for (int i = 0; i < togglers.length; i++){
                            togglers[i] = false;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                imgview = findViewById(R.id.imageView);
                if (togglers[0] == false) {
                    imgview.setImageResource(R.drawable.cracking);
                } else if (togglers[0] == true) {
                    imgview.setImageResource(R.drawable.cracking_clicked);
                }
                dry_click=findViewById(R.id.imageView_dry);
                if (togglers[1] == false) {
                    dry_click.setImageResource(R.drawable.dry);
                } else if (togglers[1] == true) {
                    dry_click.setImageResource(R.drawable.dry_clicked);
                }
                oozing_click=findViewById(R.id.imageView_oozing);
                if (togglers[2] == false) {
                    oozing_click.setImageResource(R.drawable.oozing);
                } else if (togglers[2] == true) {
                    oozing_click.setImageResource(R.drawable.oozing_clicked);
                }
                bleeding_click=findViewById(R.id.imageView_bleeding);
                if (togglers[3] == false) {
                    bleeding_click.setImageResource(R.drawable.bleeding);
                } else if (togglers[3] == true) {
                    bleeding_click.setImageResource(R.drawable.bleeding_ticked);
                }
                flaking_click=findViewById(R.id.imageView_flaking);
                if (togglers[4] == false) {
                    flaking_click.setImageResource(R.drawable.flaking);
                } else if (togglers[4] == true) {
                    flaking_click.setImageResource(R.drawable.flaking_clicked);
                }
                itchy_click=findViewById(R.id.imageView_itchy);
                if (togglers[5] == false) {
                    itchy_click.setImageResource(R.drawable.itchy);
                } else if (togglers[5] == true) {
                    itchy_click.setImageResource(R.drawable.itchy_ticked);
                }
                medicine_click=findViewById(R.id.imageView_medicine);
                if (togglers[6] == false) {
                    medicine_click.setImageResource(R.drawable.medicine);
                } else if (togglers[6] == true) {
                    medicine_click.setImageResource(R.drawable.medicine_clicked);
                }
                ointment_click=findViewById(R.id.imageView_ointment);
                if (togglers[7] == false) {
                    ointment_click.setImageResource(R.drawable.ointment);
                } else if (togglers[7] == true) {
                    ointment_click.setImageResource(R.drawable.ointment_clicked);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    public void takephoto(View view) {
        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);

        startActivity(intent);
    }


    public void Go(View view) {//Add entry
        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
        String score = getScore();
        try {MainActivity.makeDay(score);} catch (SQLException e) {e.printStackTrace();}
        startActivity(intent);
    }
    public void Logo(View view){
        Intent intent = new Intent(CalendarActivity.this, ActivityOptions.class);
        startActivity(intent);
    }

    public void buttonclick(){
        imgview = findViewById(R.id.imageView);
        if (togglers[0] == false) {
            imgview.setImageResource(R.drawable.cracking);
        } else if (togglers[0] == true) {
            imgview.setImageResource(R.drawable.cracking_clicked);
        }
        imgview.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view){
                                           if (togglers[0] == false) {
                                               togglers[0] = true;
                                               imgview.setImageResource(R.drawable.cracking_clicked);
                                           } else if (togglers[0] == true) {
                                               togglers[0] = false;
                                               imgview.setImageResource(R.drawable.cracking);
                                           }
                                       }
                                   }
        );
    }

    public void buttonclick_dry(){
        dry_click=findViewById(R.id.imageView_dry);
        if (togglers[1] == false) {
            dry_click.setImageResource(R.drawable.dry);
        } else if (togglers[1] == true) {
            dry_click.setImageResource(R.drawable.dry_clicked);
        }
        dry_click.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view){
                                             if (togglers[1] == false) {
                                                 togglers[1] = true;
                                                 dry_click.setImageResource(R.drawable.dry_clicked);
                                             } else if (togglers[1] == true) {
                                                 togglers[1] = false;
                                                 dry_click.setImageResource(R.drawable.dry);
                                             }
                                         }
                                     }
        );
    }

    public void buttonclick_oozing(){
        oozing_click=findViewById(R.id.imageView_oozing);
        if (togglers[2] == false) {
            oozing_click.setImageResource(R.drawable.oozing);
        } else if (togglers[2] == true) {
            oozing_click.setImageResource(R.drawable.oozing_clicked);
        }
        oozing_click.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view){
                                                if (togglers[2] == false) {
                                                    togglers[2] = true;
                                                    oozing_click.setImageResource(R.drawable.oozing_clicked);
                                                } else if (togglers[2] == true) {
                                                    togglers[2] = false;
                                                    oozing_click.setImageResource(R.drawable.oozing);
                                                }
                                            }
                                        }
        );
    }

    public void buttonclick_bleeding(){
        bleeding_click=findViewById(R.id.imageView_bleeding);
        if (togglers[3] == false) {
            bleeding_click.setImageResource(R.drawable.bleeding);
        } else if (togglers[3] == true) {
            bleeding_click.setImageResource(R.drawable.bleeding_ticked);
        }
        bleeding_click.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view){
                                                  if (togglers[3] == false) {
                                                      togglers[3] = true;
                                                      bleeding_click.setImageResource(R.drawable.bleeding_ticked);
                                                  } else if (togglers[3] == true) {
                                                      togglers[3] = false;
                                                      bleeding_click.setImageResource(R.drawable.bleeding);
                                                  }
                                              }
                                          }
        );
    }

    public void buttonclick_flaking(){
        flaking_click=findViewById(R.id.imageView_flaking);
        if (togglers[4] == false) {
            flaking_click.setImageResource(R.drawable.flaking);
        } else if (togglers[4] == true) {
            flaking_click.setImageResource(R.drawable.flaking_clicked);
        }
        flaking_click.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view){
                                                 if (togglers[4] == false) {
                                                     togglers[4] = true;
                                                     flaking_click.setImageResource(R.drawable.flaking_clicked);
                                                 } else if (togglers[4] == true) {
                                                     togglers[4] = false;
                                                     flaking_click.setImageResource(R.drawable.flaking);
                                                 }
                                             }
                                         }
        );
    }

    public void buttonclick_itchy(){
        itchy_click=findViewById(R.id.imageView_itchy);
        if (togglers[5] == false) {
            itchy_click.setImageResource(R.drawable.itchy);
        } else if (togglers[5] == true) {
            itchy_click.setImageResource(R.drawable.itchy_ticked);
        }
        itchy_click.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view){
                                               if (togglers[5] == false) {
                                                   togglers[5] = true;
                                                   itchy_click.setImageResource(R.drawable.itchy_ticked);
                                               } else if (togglers[5] == true) {
                                                   togglers[5] = false;
                                                   itchy_click.setImageResource(R.drawable.itchy);
                                               }
                                           }
                                       }
        );
    }

    public void buttonclick_medicine(){
        medicine_click=findViewById(R.id.imageView_medicine);
        if (togglers[6] == false) {
            medicine_click.setImageResource(R.drawable.medicine);
        } else if (togglers[6] == true) {
            medicine_click.setImageResource(R.drawable.medicine_clicked);
        }
        medicine_click.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view){
                                                  if (togglers[6] == false) {
                                                      togglers[6] = true;
                                                      medicine_click.setImageResource(R.drawable.medicine_clicked);
                                                  } else if (togglers[6] == true) {
                                                      togglers[6] = false;
                                                      medicine_click.setImageResource(R.drawable.medicine);
                                                  }
                                              }
                                          }
        );
    }

    public void buttonclick_ointment(){
        ointment_click=findViewById(R.id.imageView_ointment);
        if (togglers[7] == false) {
            ointment_click.setImageResource(R.drawable.ointment);
        } else if (togglers[7] == true) {
            ointment_click.setImageResource(R.drawable.ointment_clicked);
        }
        ointment_click.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view){
                                                  if (togglers[7] == false) {
                                                      togglers[7] = true;
                                                      ointment_click.setImageResource(R.drawable.ointment_clicked);
                                                  } else if (togglers[7] == true) {
                                                      togglers[7] = false;
                                                      ointment_click.setImageResource(R.drawable.ointment);
                                                  }
                                              }
                                          }
        );
    }

    public String getScore(){
        String score="";
        if (togglers[0]==false){score=score+'0';}else{score=score+'1';}
        if (togglers[1]==false){score=score+'0';}else{score=score+'1';}
        if (togglers[2]==false){score=score+'0';}else{score=score+'1';}
        if (togglers[3]==false){score=score+'0';}else{score=score+'1';}
        if (togglers[4]==false){score=score+'0';}else{score=score+'1';}
        if (togglers[5]==false){score=score+'0';}else{score=score+'1';}
        if (togglers[6]==false){score=score+'0';}else{score=score+'1';}
        if (togglers[7]==false){score=score+'0';}else{score=score+'1';}


        return score;
    }
}