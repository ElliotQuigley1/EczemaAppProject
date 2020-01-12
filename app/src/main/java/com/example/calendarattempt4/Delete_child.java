package com.example.calendarattempt4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Delete_child extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_child);
    }

    public void yes_button(View view) {
        MainActivity.C.delete();
        Intent intent = new Intent(Delete_child.this, MainActivity.class);
        startActivity(intent);
    }

    public void no_button(View view) {
        Intent intent = new Intent(Delete_child.this, MainActivity.class);
        startActivity(intent);
    }
}
