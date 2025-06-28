package com.zain.myapp1;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.zain.myapp1.sqlhelpers.ProductDatabaseHelper;

public class sqllite_crud extends AppCompatActivity {

    ProductDatabaseHelper db;
    EditText editId, editName, editPrice;
    Button btnSave, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqllite_crud);
        db = new ProductDatabaseHelper(this);

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        editPrice = findViewById(R.id.editPrice);
        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String priceStr = editPrice.getText().toString();
            if (name.isEmpty() || priceStr.isEmpty()) {
                showToast("Please enter name and price");
                return;
            }
            boolean inserted = db.insertProduct(name, Double.parseDouble(priceStr));
            showToast(inserted ? "Product saved" : "Failed to save");
        });

        btnView.setOnClickListener(v -> {
            Cursor res = db.getAllProducts();
            if (res.getCount() == 0) {
                showMessage("No Data", "No products found.");
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("ID: ").append(res.getInt(0)).append("\n");
                buffer.append("Name: ").append(res.getString(1)).append("\n");
                buffer.append("Price: ").append(res.getDouble(2)).append("\n\n");
            }

            showMessage("Products", buffer.toString());
        });

        btnUpdate.setOnClickListener(v -> {
            String id = editId.getText().toString();
            String name = editName.getText().toString();
            String priceStr = editPrice.getText().toString();
            if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty()) {
                showToast("Fill all fields to update");
                return;
            }

            boolean updated = db.updateProduct(id, name, Double.parseDouble(priceStr));
            showToast(updated ? "Product updated" : "Update failed");
        });

        btnDelete.setOnClickListener(v -> {
            String id = editId.getText().toString();
            if (id.isEmpty()) {
                showToast("Enter product ID to delete");
                return;
            }

            boolean deleted = db.deleteProduct(id);
            showToast(deleted ? "Product deleted" : "Delete failed");
        });
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    void showMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}
