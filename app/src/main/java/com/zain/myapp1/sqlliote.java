package com.zain.myapp1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.zain.myapp1.sqlhelpers.MyDatabaseHelper;

public class sqlliote extends AppCompatActivity {
    EditText nameInput, ageInput;
    Button saveButton;
    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlliote);
        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);
        saveButton = findViewById(R.id.saveButton);





    }
}