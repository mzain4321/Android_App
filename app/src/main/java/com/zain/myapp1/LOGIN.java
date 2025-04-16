package com.zain.myapp1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LOGIN extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private CheckBox rememberMeCheckbox;
    private Button loginButton;
    private TextView signupText;
    private String storedUsername, storedEmail, storedPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailinput);
        passwordInput = findViewById(R.id.passwordinput);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);
       // EditText emailInput = findViewById(R.id.emailinput);
        //EditText passwordInput = findViewById(R.id.passwordinput);
        // Button loginButton = findViewById(R.id.loginButton);
        TextView signupText = findViewById(R.id.signupText);

//        loginButton.setOnClickListener(v -> {
//            String email = emailInput.getText().toString().trim();
//            String password = passwordInput.getText().toString().trim();
//
//            if (email.equals("zain@gmail.com") && password.equals("123")) {
//                Intent intent = new Intent(LOGIN.this, Dashboard.class);
//                startActivity(intent);
//                finish();
//            } else {
//                Toast.makeText(LOGIN.this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
//            }
//        });
        Intent intent = getIntent();
        storedUsername = intent.getStringExtra("username");
        storedEmail = intent.getStringExtra("email");
        storedPassword = intent.getStringExtra("password");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = emailInput.getText().toString().trim();
                String enteredPassword = passwordInput.getText().toString();


                if (enteredEmail.equals(storedEmail)) {
                    if (enteredPassword.equals(storedPassword)) {
                        Toast.makeText(LOGIN.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent dashboardIntent = new Intent(LOGIN.this, Dashboard.class);
                        dashboardIntent.putExtra("username", storedUsername);
                        startActivity(dashboardIntent);
                        finish();
                    } else {
                        Toast.makeText(LOGIN.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LOGIN.this, "Email not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupText.setOnClickListener(v -> {
            //Intent intent1 = new Intent(LOGIN.this, MainActivity.class);
           // startActivity(intent1);
            showSignInDialog();
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign In")
                .setMessage("You have clicked Sign In!")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
