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
    private Button projectsButton, fragButton, calculatorButton, sqlliteButton ,shrePrefButton ,sqlCrud,RecycleView, fireBaseCrud,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        welcomeText = findViewById(R.id.welcome_text);
        logoutButton = findViewById(R.id.logout_button);
        projectsButton = findViewById(R.id.projects_button);
        fragButton = findViewById(R.id.frag_button);
        calculatorButton = findViewById(R.id.calculator_button);
        sqlliteButton = findViewById(R.id.settings_button);
        shrePrefButton= findViewById(R.id.share_pref_button);
        sqlCrud=findViewById(R.id.sqlcrud);
        RecycleView=findViewById(R.id.recyclerView);
        fireBaseCrud=findViewById(R.id.firebasecrud);
        otp=findViewById(R.id.otp);

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
        sqlliteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, sqlsignup.class);
                startActivity(intent);
            }
        });
        shrePrefButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, sharepref.class);
                startActivity(intent);
            }
        });
        otp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, otp.class);
                startActivity(intent);
            }
        });
        sqlCrud.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, sqllite_crud.class);
                startActivity(intent);
            }
        });
        projectsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Media_page.class);
                startActivity(intent);
            }
        });
        RecycleView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, recyclerView.class);
                startActivity(intent);
            }
        });
        fireBaseCrud.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, fireBaseCrud.class);
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