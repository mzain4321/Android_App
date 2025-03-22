package com.zain.myapp1;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calculator extends AppCompatActivity {
    private EditText display;
    private String input = "";
    private String operator = "";
    private double num1 = 0;
    private double num2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        display = findViewById(R.id.display);
        setButtonClickListener(R.id.btn_0, "0");
        setButtonClickListener(R.id.btn_1, "1");
        setButtonClickListener(R.id.btn_2, "2");
        setButtonClickListener(R.id.btn_3, "3");
        setButtonClickListener(R.id.btn_4, "4");
        setButtonClickListener(R.id.btn_5, "5");
        setButtonClickListener(R.id.btn_6, "6");
        setButtonClickListener(R.id.btn_7, "7");
        setButtonClickListener(R.id.btn_8, "8");
        setButtonClickListener(R.id.btn_9, "9");
        setButtonClickListener(R.id.btn_decimal, ".");
        setButtonClickListener(R.id.btn_add, "+");
        setButtonClickListener(R.id.btn_subtract, "-");
        setButtonClickListener(R.id.btn_multiply, "*");
        setButtonClickListener(R.id.btn_divide, "/");
        findViewById(R.id.btn_equals).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btn_clear).setOnClickListener(v -> clearDisplay());
        findViewById(R.id.btn_backspace).setOnClickListener(v -> backspace());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setButtonClickListener(int buttonId, final String value) {
        findViewById(buttonId).setOnClickListener(v -> appendToDisplay(value));
    }
    private void appendToDisplay(String value) {
        input += value;
        display.setText(input);
    }
    private void backspace() {
        if (!input.isEmpty()) {
            input = input.substring(0, input.length() - 1);
            display.setText(input.isEmpty() ? "0" : input);
        }
    }

    private void clearDisplay() {
        input = "";
        operator = "";
        num1 = 0;
        num2 = 0;
        display.setText("0");
    }

    private void calculateResult() {
        if (!input.isEmpty()) {
            String[] parts = input.split("[-+*/]");
            if (parts.length == 2) {
                num1 = Double.parseDouble(parts[0]);
                num2 = Double.parseDouble(parts[1]);
                operator = input.replaceAll("[0-9.]", "");

                double result = 0;
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                input = String.valueOf(result);
            }
        }
    }
}