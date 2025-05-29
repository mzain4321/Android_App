package com.zain.myapp1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LOGIN extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private CheckBox rememberMeCheckbox;
    private Button loginButton;
    private TextView signupText;
    private String storedUsername, storedEmail, storedPassword;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        emailInput = findViewById(R.id.emailinput);
        passwordInput = findViewById(R.id.passwordinput);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);
       // EditText emailInput = findViewById(R.id.emailinput);
        //EditText passwordInput = findViewById(R.id.passwordinput);
        // Button loginButton = findViewById(R.id.loginButton);
        TextView signupText = findViewById(R.id.signupText);
        auth=FirebaseAuth.getInstance();
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


               // if (enteredEmail.equals(storedEmail)) {
                 //   if (enteredPassword.equals(storedPassword)) {
                //     Toast.makeText(LOGIN.this, "Login Successful", Toast.LENGTH_SHORT).show();
                //Intent dashboardIntent = new Intent(LOGIN.this, Dashboard.class);
                  //      dashboardIntent.putExtra("username", storedUsername);
                    //    startActivity(dashboardIntent);
                      //  finish();
                   // } else {
                    //    Toast.makeText(LOGIN.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    //}
                //}
                //else {
                  //  Toast.makeText(LOGIN.this, "Email not found!", Toast.LENGTH_SHORT).show();
                //}
                if(!enteredEmail.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()){
                    if(!enteredPassword.isEmpty()){
                        auth.signInWithEmailAndPassword(enteredEmail,enteredPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                              //  Toast.makeText(LOGIN.this, "sigin success", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(LOGIN.this, Dashboard.class);
                                  //                        startActivity(intent);
                                    //                 finish();
                                String userId = auth.getCurrentUser().getUid();
                                db.collection("users").document(userId).get()
                                        .addOnSuccessListener(documentSnapshot -> {
                                            if (documentSnapshot.exists()) {
                                                String username = documentSnapshot.getString("username");
                                                Toast.makeText(LOGIN.this, "Sign in success", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(LOGIN.this, Dashboard.class);
                                                intent.putExtra("username", username);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                    Toast.makeText(LOGIN.this, "User data not found", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(LOGIN.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LOGIN.this, "sigin failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(LOGIN.this, "password should not be empty", Toast.LENGTH_SHORT).show();
                    }
                } else if (enteredEmail.isEmpty()) {
                    Toast.makeText(LOGIN.this, "please enter email", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LOGIN.this, "enter valid email", Toast.LENGTH_SHORT).show();
                }
                // if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                 //   Toast.makeText(LOGIN.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                   // return;
                //}

                // Check user in Firestore
                // //   db.collection("users")
                //
                //       .whereEqualTo("email", enteredEmail)
                 //       .get()
                   //     .addOnSuccessListener(queryDocumentSnapshots -> {
                  //          if (!queryDocumentSnapshots.isEmpty()) {
                    //            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                      //          String storedPassword = document.getString("password");
                        //        String storedUsername = document.getString("username");
//
  //                              if (enteredPassword.equals(storedPassword)) {
    //                                Toast.makeText(LOGIN.this, "Login Successful", Toast.LENGTH_SHORT).show();
      //                              Intent intent = new Intent(LOGIN.this, Dashboard.class);
        //                            intent.putExtra("username", storedUsername);
          //                          startActivity(intent);
            //                        finish();
              //                  } else {
                //                    Toast.makeText(LOGIN.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                  //              }
                    //        } else {
                      //          Toast.makeText(LOGIN.this, "Emai not found!", Toast.LENGTH_SHORT).show();
                        //    }
                        //})
                        //.addOnFailureListener(e -> {
                          //  Toast.makeText(LOGIN.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //});
            }
        });

        signupText.setOnClickListener(v -> {
          Intent intent1 = new Intent(LOGIN.this, MainActivity.class);
           startActivity(intent1);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
