package com.zain.myapp1;


import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class sharepref extends AppCompatActivity {

    EditText editName;
    TextView txtDisplay;
    Button btnSave, btnLoad;

    // SharedPreferences file name
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String KEY_NAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharepref);

        editName = findViewById(R.id.editName);
        txtDisplay = findViewById(R.id.txtDisplay);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);

        // Save data to SharedPreferences
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                if (!name.isEmpty()) {
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_NAME, name);
                    editor.apply();

                    Toast.makeText(sharepref.this, "Saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(sharepref.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load data from SharedPreferences
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String savedName = sharedPreferences.getString(KEY_NAME, "No name saved");

                txtDisplay.setText("Saved Name: " + savedName);
            }
        });
    }
}
