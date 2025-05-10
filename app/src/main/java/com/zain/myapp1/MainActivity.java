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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput, emailInput, passwordInput, confirmPasswordInput;
    private CheckBox termsCheckbox;
    private Button signUpButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();
//        Button signUpButton = findViewById(R.id.signUpButton);
        TextView loginText = findViewById(R.id.logintext);
        usernameInput = findViewById(R.id.usernameinput);
        emailInput = findViewById(R.id.emailinput);
        passwordInput = findViewById(R.id.paswordinput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        signUpButton = findViewById(R.id.signUpButton);
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LOGIN.class);
//                startActivity(intent);
//            }
//        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();


                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(MainActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!termsCheckbox.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please agree to 'Remember Me' (or Terms)", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                         //   Toast.makeText(MainActivity.this,"signup successful",Toast.LENGTH_SHORT).show();
                           // Intent intent = new Intent(MainActivity.this, LOGIN.class);
                           //  startActivity(intent);
                            // finish();
                            String uid = auth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", username);
                            user.put("email", email);

                            db.collection("users").document(uid)
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(MainActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, LOGIN.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(MainActivity.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                        else{
                            Toast.makeText(MainActivity.this,"signup failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
               // Intent intent = new Intent(MainActivity.this, LOGIN.class);
                //intent.putExtra("username", username);
                //intent.putExtra("email", email);
                //intent.putExtra("password", password);
                //Map<String, Object> user = new HashMap<>();
                //user.put("username", username);
                //user.put("email", email);
                //user.put("password", password);

                //db.collection("users")
                  //      .add(user)
                    //    .addOnSuccessListener(documentReference -> {
                      //      Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        //    Intent intent = new Intent(MainActivity.this, LOGIN.class);
                          //  startActivity(intent);
                           // finish();
                        //})
                        //.addOnFailureListener(e -> {
                          //  Toast.makeText(MainActivity.this, "Error: " , Toast.LENGTH_SHORT).show();
                        //});

                // startActivity(intent);
                //finish();
            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LOGIN.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}