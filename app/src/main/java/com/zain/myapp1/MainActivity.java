package com.zain.myapp1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput, emailInput, passwordInput, confirmPasswordInput, phoneinput;
    private CheckBox termsCheckbox;
    private Button signUpButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        TextView loginText = findViewById(R.id.logintext);
        usernameInput = findViewById(R.id.usernameinput);
        emailInput = findViewById(R.id.emailinput);
        passwordInput = findViewById(R.id.paswordinput);
        phoneinput = findViewById(R.id.phoneinput);
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
                String phoneNumber = phoneinput.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty()) {
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

                sendOTP(phoneNumber, username, email, password);
            }
        });

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LOGIN.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sendOTP(String phoneNumber, String username, String email, String password) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+92" + phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        // Auto-verification succeeded
                        signUpUser(username, email, password);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(MainActivity.this, "OTP Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verifId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        verificationId = verifId;
                        showOTPDialog(username, email, password);
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void showOTPDialog(String username, String email, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter 6-digit OTP");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("123456");
        builder.setView(input);

        builder.setPositiveButton("Verify", (dialog, which) -> {
            String otp = input.getText().toString().trim();
            if (otp.length() == 6 && verificationId != null) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                signUpUser(username, email, password);
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid 6-digit OTP", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void signUpUser(String username, String email, String password) {
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
    }
}
