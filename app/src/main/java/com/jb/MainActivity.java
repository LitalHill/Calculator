package com.jb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final Map<Integer, String> idToText = new HashMap<>();
    private EditText expression;

    private static final int[] buttonIds = {
            R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4,
            R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9,
            R.id.bt_plus, R.id.bt_minus, R.id.bt_mult, R.id.bt_div, R.id.bt_dot
    };

    private static final String[] buttonTexts = {
            "0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9",
            "+", "-", "*", "/", "."
    };

    static {
        for (int i = 0; i < buttonIds.length; i++) {
            idToText.put(buttonIds[i], buttonTexts[i]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = findViewById(R.id.et_expression);

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(v -> onButtonClick(button.getText().toString()));
        }

        // Set click listener for equals button
        findViewById(R.id.bt_equals).setOnClickListener(v -> calculateResult());

        // Set click listener for clear button
        findViewById(R.id.bt_clear).setOnClickListener(v -> clearExpression());
    }

    private void onButtonClick(String buttonText) {
        expression.append(buttonText);
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult() {
        try {
            String expressionText = expression.getText().toString();
            //evaluation
            double result = eval(expressionText);
            expression.setText(String.valueOf(result));
        } catch (Exception e) {
            expression.setText("Error");
        }
    }

    private double eval(String expressionText) {
        // Split the expression into operands and operator
        String[] tokens = expressionText.split("(?<=[-+*/])|(?=[-+*/])");

        try {
            double operand1 = Double.parseDouble(tokens[0]);
            double operand2 = Double.parseDouble(tokens[2]);
            String operator = tokens[1];

            // Perform the operation
            switch (operator) {
                case "+":
                    return operand1 + operand2;
                case "-":
                    return operand1 - operand2;
                case "*":
                    return operand1 * operand2;
                case "/":
                    if (operand2 != 0) {
                        return operand1 / operand2;
                    } else {
                        // Handle division by zero error
                        return 0;
                    }
                default:
                    // Handle unknown operator error
                    return 0;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return 0;
        }
    }

    private void clearExpression() {
        expression.setText("");
    }

}
