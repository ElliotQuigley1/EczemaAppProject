package com.example.calendarattempt4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class ActivityOptions extends AppCompatActivity {
    private static ImageView imgview;
    private static ImageView imgview2;

    private static ImageView dry;
    private static ImageView dry_click;

    private static ImageView oozing;
    private static ImageView oozing_click;

    private int current_image;
    private int current_image_dry;
    private int current_image_oozing;

    int[] images={R.drawable.cracking,R.drawable.cracking_clicked};
    int[] images_dry={R.drawable.dry,R.drawable.dry_clicked};
    int[] images_oozing={R.drawable.oozing,R.drawable.oozing_clicked};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        buttonclick();
        buttonclick_dry();
        buttonclick_oozing();
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
}