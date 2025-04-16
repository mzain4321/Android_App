package com.zain.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {
    private TextView welcomeText;
    private ListView recentList;
    private Button logoutButton;
    private Button projectsButton, fragButton, calculatorButton, settingsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        welcomeText = findViewById(R.id.welcome_text);
        recentList = findViewById(R.id.recent_list);
        logoutButton = findViewById(R.id.logout_button);
        projectsButton = findViewById(R.id.projects_button);
        fragButton = findViewById(R.id.frag_button);
        calculatorButton = findViewById(R.id.calculator_button);
        settingsButton = findViewById(R.id.settings_button);

        String username = getIntent().getStringExtra("username");
        if (username == null || username.isEmpty()) {
            username = "User";
        }
        welcomeText.setText("Welcome back, " + username + "!");

        calculatorButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Calculator.class);
                startActivity(intent);
            }
        });
        fragButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, fragmentpage.class);
                startActivity(intent);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, LOGIN.class);
                startActivity(intent);
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}