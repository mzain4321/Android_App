package com.zain.myapp1;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class fireBaseCrud extends AppCompatActivity {

    EditText etId, etName, etEmail;
    Button btnCreate, btnRead, btnUpdate, btnDelete;
    TextView tvResult;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_crud);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        tvResult = findViewById(R.id.tvResult);

        db = FirebaseFirestore.getInstance();

        btnCreate.setOnClickListener(v -> createUser());
        btnRead.setOnClickListener(v -> readUser());
        btnUpdate.setOnClickListener(v -> updateUser());
        btnDelete.setOnClickListener(v -> deleteUser());
    }

    private void createUser() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("username", name);
        user.put("email", email);

        db.collection("users").document(id).set(user)
                .addOnSuccessListener(unused -> tvResult.setText("User created!"))
                .addOnFailureListener(e -> tvResult.setText("Error: " + e.getMessage()));
    }

    private void readUser() {
        String id = etId.getText().toString().trim();

        if (id.isEmpty()) {
            // Read all users
            db.collection("users").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            StringBuilder allUsers = new StringBuilder();
                            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                String docId = doc.getId();
                                String name = doc.getString("username");
                                String email = doc.getString("email");

                                allUsers.append("ID: ").append(docId)
                                        .append("\nName: ").append(name)
                                        .append("\nEmail: ").append(email)
                                        .append("\n\n");
                            }
                            tvResult.setText(allUsers.toString());
                        } else {
                            tvResult.setText("No users found.");
                        }
                    })
                    .addOnFailureListener(e -> tvResult.setText("Error: " + e.getMessage()));
        } else {
            // Read one user by ID
            db.collection("users").document(id).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("username");
                            String email = documentSnapshot.getString("email");
                            tvResult.setText("Name: " + name + "\nEmail: " + email);
                        } else {
                            tvResult.setText("No such document");
                        }
                    })
                    .addOnFailureListener(e -> tvResult.setText("Error: " + e.getMessage()));
        }
    }

    private void updateUser() {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        if (id.isEmpty()) {
            Toast.makeText(this, "Enter ID to update", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("username", name);
        user.put("email", email);

        db.collection("users").document(id).update(user)
                .addOnSuccessListener(unused -> tvResult.setText("User updated!"))
                .addOnFailureListener(e -> tvResult.setText("Error: " + e.getMessage()));
    }

    private void deleteUser() {
        String id = etId.getText().toString().trim();

        if (id.isEmpty()) {
            // Delete all users
            db.collection("users").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                db.collection("users").document(doc.getId()).delete();
                            }
                            tvResult.setText("All users deleted!");
                        } else {
                            tvResult.setText("No users to delete.");
                        }
                    })
                    .addOnFailureListener(e -> tvResult.setText("Error: " + e.getMessage()));
        } else {
            // Delete specific user
            db.collection("users").document(id).delete()
                    .addOnSuccessListener(unused -> tvResult.setText("User deleted!"))
                    .addOnFailureListener(e -> tvResult.setText("Error: " + e.getMessage()));
        }
    }

}
