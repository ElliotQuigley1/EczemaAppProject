package com.example.calendarattempt4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Log_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void log_in_button (View view) {
        TextView username_input = (TextView) findViewById(R.id.username_input);
        String username_string = username_input.getText().toString();
        TextView password_input = (TextView) findViewById(R.id.password_input);
        String password_string = password_input.getText().toString();
        try {
            if (MainActivity.getP().login(username_string, password_string)){
                Toast.makeText(this, "LOGGED IN: " + username_string, Toast.LENGTH_SHORT).show();
                SaveSharedPreference.setUserName(Log_in.this,username_string);
                SaveSharedPreference.setPassword(Log_in.this,password_string);
                Intent intent = new Intent(Log_in.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this,"WRONG LOG IN" , Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
