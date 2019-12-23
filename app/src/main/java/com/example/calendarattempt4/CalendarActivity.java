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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import static com.example.calendarattempt4.MainActivity.checkDay_on_state_change;

/**
 * Created by ganesh on 6/10/2017.
 */

public class CalendarActivity extends AppCompatActivity {
    // Declares view objects
    private CalendarView mCalendarView;
    private String date_chosen;
    // Declares array for icons state
    private boolean new_data = true;
    private boolean togglers[] = {false,false,false,false,false,false,false,false};
    int[] R_id_array = {R.id.imageView_cracking,R.id.imageView_dry,R.id.imageView_oozing,R.id.imageView_bleeding,R.id.imageView_flaking,R.id.imageView_itchy,R.id.imageView_medicine,R.id.imageView_ointment};
    int[] drawable = {R.drawable.cracking,R.drawable.dry,R.drawable.oozing,R.drawable.bleeding,R.drawable.flaking,R.drawable.itchy,R.drawable.medicine,R.drawable.ointment};
    int[] drawable_clicked = {R.drawable.cracking_clicked,R.drawable.dry_clicked,R.drawable.oozing_clicked,R.drawable.bleeding_clicked,R.drawable.flaking_clicked,R.drawable.itchy_clicked,R.drawable.medicine_clicked,R.drawable.ointment_clicked};
    // Declares camera function parameters
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_layout);

        // Camera OnClickListener
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

        // Calendar OnClickListener
        mCalendarView = findViewById(R.id.smth);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = year + "/" + month + "/"+ dayOfMonth ;
                date_chosen = date;

                display_answers();

            }
        });

        // Icons OnClickListener
        for (int i = 0; i<togglers.length; i++) {
            final int j = i;
            ImageView imgview = findViewById(R_id_array[i]);
            imgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if (togglers[j] == false) {
                        togglers[j] = true;
                    } else if (togglers[j] == true) {
                        togglers[j] = false;
                    }
                    set_icons(j);
                }
            });
        }

        // Initial icon state for current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        date_chosen = sdf.format(new Date(mCalendarView.getDate()));
        display_answers();

    }


    // Saves answer from database into variable
    public void display_answers() {
        try {
            if (checkDay_on_state_change(date_chosen)) {
                new_data = false;
                // Converts 1 and 0 into boolean variable array
                for (int i = 0; i<MainActivity.D.answers.length(); i++) {
                    if(MainActivity.D.answers.charAt(i) == '1') {
                        togglers[i] = true;
                    } else if (MainActivity.D.answers.charAt(i) == '0') {
                        togglers[i] = false;
                    }
                }
            } else {
                new_data = true;
                for (int i = 0; i < togglers.length; i++){
                    togglers[i] = false;
                }
                Toast.makeText(this, "No data entered for this date", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Refresh all icons state
        for (int i = 0; i<togglers.length; i++) {
            set_icons(i);
        }
    }

    // Refresh icon state
    public void set_icons(int icon_num) {
        ImageView imgview = findViewById(R_id_array[icon_num]);
        if (togglers[icon_num] == false) {
            imgview.setImageResource(drawable[icon_num]);
        } else if (togglers[icon_num] == true) {
            imgview.setImageResource(drawable_clicked[icon_num]);
        }
    }



    // Add entry to database depending if selected has existing data
    // Shows user message for success / failure
    public void Save_entry(View view) {
        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
        String score="";
        for (int i = 0; i < togglers.length; i++){
            if (togglers[i]==false){score=score+'0';
            } else{
                score=score+'1';
            }
        }
        try {
            if (new_data) {
                MainActivity.makeDay(score);
            } else {
                MainActivity.rewriteDay(score);
            }
            // Shows user message for success / failure
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            // Shows user message for success / failure
            Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();
        }
        // Returns to main page
        startActivity(intent);
    }



    // Camera function settings
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
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











/*


    private static ImageView imgview;
    private static ImageView dry_click;
    private static ImageView oozing_click;
    private static ImageView bleeding_click;
    private static ImageView flaking_click;
    private static ImageView itchy_click;
    private static ImageView medicine_click;
    private static ImageView ointment_click;

    private Button takephoto;
                takephoto = (Button) findViewById(R.id.takephoto);


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

    int[] images={R.drawable.cracking,R.drawable.cracking_clicked};
    int[] images_dry={R.drawable.dry,R.drawable.dry_clicked};
    int[] images_oozing={R.drawable.oozing,R.drawable.oozing_clicked};
    int[] images_bleeding={R.drawable.bleeding,R.drawable.bleeding_clicked};
    int[] images_flaking={R.drawable.flaking,R.drawable.flaking_clicked};
    int[] images_itchy={R.drawable.itchy,R.drawable.itchy_clicked};
    int[] images_medicine={R.drawable.medicine,R.drawable.medicine_clicked};
    int[] images_ointment={R.drawable.ointment,R.drawable.ointment_clicked};

    String loop_array[] = {"cracking","dry","oozing","bleeding","flaking","itchy","medicine","ointment"};







    public void takephoto(View view) {
        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);

        startActivity(intent);
    }




    public void Logo(View view){
        Intent intent = new Intent(CalendarActivity.this, ActivityOptions.class);
        startActivity(intent);
    }



    public void buttonclick(){
        imgview = findViewById(R.id.imageView_cracking);
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
            bleeding_click.setImageResource(R.drawable.bleeding_clicked);
        }
        bleeding_click.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view){
                                                  if (togglers[3] == false) {
                                                      togglers[3] = true;
                                                      bleeding_click.setImageResource(R.drawable.bleeding_clicked);
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
            itchy_click.setImageResource(R.drawable.itchy_clicked);
        }
        itchy_click.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view){
                                               if (togglers[5] == false) {
                                                   togglers[5] = true;
                                                   itchy_click.setImageResource(R.drawable.itchy_clicked);
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









    imgview = findViewById(R.id.imageView_cracking);
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
        bleeding_click.setImageResource(R.drawable.bleeding_clicked);
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
        itchy_click.setImageResource(R.drawable.itchy_clicked);
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

 */
}


