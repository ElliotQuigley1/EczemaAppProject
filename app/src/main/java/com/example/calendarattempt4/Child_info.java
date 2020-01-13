package com.example.calendarattempt4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Child_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info);
    }

    public void makeChild_button(View view) throws SQLException {
        TextView name = findViewById(R.id.name_input);
        TextView age = findViewById(R.id.age_input);
        TextView height = findViewById(R.id.height_input);
        TextView weight = findViewById(R.id.weight_input);
        if(name.getText().toString().isEmpty()){
            name.setError( "Name is required!" );
        }else if(Integer.parseInt(age.getText().toString()) <0) {
            age.setError("Value not valid!");
        }else if(Integer.parseInt(height.getText().toString()) <=0) {
            height.setError("Value not valid!");
        }else if(Integer.parseInt(weight.getText().toString()) <=0) {
            weight.setError("Value not valid!");
        } else{
            if (MainActivity.getC().create(name.getText().toString(), Integer.parseInt(age.getText().toString()), Integer.parseInt(height.getText().toString()), Integer.parseInt(weight.getText().toString()), MainActivity.getP().getParent_ID()) == false){
                Toast.makeText(this, "Child already exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Child created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Child_info.this, MainActivity.class);
                startActivity(intent);
            }
        }

    }
}
