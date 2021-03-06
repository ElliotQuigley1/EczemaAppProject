package com.example.calendarattempt4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Delete_child extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_child);
    }

    public void yes_button(View view) {
        // Deletes child data from database
        MainActivity.getC().delete();
        Intent intent = new Intent(Delete_child.this, MainActivity.class);
        startActivity(intent);
    }

    public void no_button(View view) {
        // returns to previous activity
        Intent intent = new Intent(Delete_child.this, MainActivity.class);
        startActivity(intent);
    }
}
