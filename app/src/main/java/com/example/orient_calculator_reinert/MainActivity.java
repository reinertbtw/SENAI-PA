package com.example.orient_calculator_reinert;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput;
    private String currentOperator;
    private double operand1 = Double.NaN;
    private double operand2;
    private boolean resetScreen = false;
    private double memory = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Botões Numéricos
        int[] numberButtomIds = {
                R.id.btn_0,
                R.id.btn_1,
                R.id.btn_2,
                R.id.btn_3,
                R.id.btn_4,
                R.id.btn_5,
                R.id.btn_6,
                R.id.btn_7,
                R.id.btn_8,
                R.id.btn_9
        };

        for (int id : numberButtomIds) {
            findViewById(id).setOnClickListener(view ->
                    onNumberButtonClick(((Button) view) .getText().toString()));
        }

        // Virgula
        findViewById(R.id.btn_decimal).setOnClickListener(view -> onDecimalButtonClick());

        // Operadores
        findViewById(R.id.btn_add).setOnClickListener(view -> onOperatorButtonClick("+"));
        findViewById(R.id.btn_subtract).setOnClickListener(view -> onOperatorButtonClick("-"));
        findViewById(R.id.btn_multiply).setOnClickListener(view -> onOperatorButtonClick("x"));
        findViewById(R.id.btn_divide).setOnClickListener(view -> onOperatorButtonClick("/"));

        // Igual
        findViewById(R.id.btn_equals).setOnClickListener(view -> onEqualButtonClick());

        // Funções Especiais
        findViewById(R.id.btn_sign).setOnClickListener(view -> onSignButtonClick());
        findViewById(R.id.btn_percent).setOnClickListener(view -> onPercentButtonClick());
        findViewById(R.id.btn_c).setOnClickListener(view -> onClearButtonClick());
        findViewById(R.id.btn_ce).setOnClickListener(view -> onClearEntryButtonClick());
        findViewById(R.id.btn_backspace).setOnClickListener(view -> onBackspaceButtonClick());
        findViewById(R.id.btn_reciprocal).setOnClickListener(view -> onReciprocalButtonClick());
        findViewById(R.id.btn_square).setOnClickListener(view -> onSquareButtonClick());
        findViewById(R.id.btn_cube_root).setOnClickListener(view -> onCubeRootButtonClick());

        // Memória
        findViewById(R.id.btn_mc).setOnClickListener(view -> onMemoryClear());
        findViewById(R.id.btn_mr).setOnClickListener(view -> onMemoryRecall());
        findViewById(R.id.btn_m_plus).setOnClickListener(view -> onMemoryAdd());
        findViewById(R.id.btn_m_minus).setOnClickListener(view -> onMemorySubtract());
        findViewById(R.id.btn_ms).setOnClickListener(view -> onMemoryStore());
        findViewById(R.id.btn_mv).setOnClickListener(view -> onMemoryView());
    }

    // Números
    private void onNumberButtonClick(String digit) {
        if (resetScreen) {
            currentInput = "";
            resetScreen = false;
        }

        if (currentInput.equals("0") && digit.equals("0")){
            return;
        }

        if (currentInput.equals("0") && !digit.equals("0")) {
            currentInput = digit;
        } else {
            currentInput += digit;
        }

        updateDisplay();
    }

    // Vírgula
    private void onDecimalButtonClick() {
        if (resetScreen) {
            currentInput = "0.";
            resetScreen = false;
            updateDisplay();
            return;
        }

        if (currentInput.contains(".")) {
            return;
        }

        if (currentInput.isEmpty()) {
            currentInput = "0.";
        } else {
            currentInput = ".";
        }
        updateDisplay();
    }

    // Operadores
    private void onOperatorButtonClick(String operator) {
        if (!Double.isNaN(operand1)) {
            onEqualButtonClick();
        }
        try {
            operand1 = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            operand1 = 0.0;
        }
        currentOperator = operator;
        resetScreen = true;
    }

    private void onEqualButtonClick() {
        if (Double.isNaN(operand1) || currentOperator.isEmpty()) {
            return;
        }

        try {
            operand2 = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            operand2 = 0.0;
        }

        double result = 0.0;

        switch (currentOperator) {
            case "+":
                result = operand1 + operand2;
            case "-":
                result = operand1 - operand2;
            case "*":
                result = operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    display.setText("Erro");
                    resetCalculator();
                    return;
                }
                result = operand1 / operand2;
                break;
        }

        currentInput = formatResult(result);
        operand1 = result;
        resetScreen = true;
        currentOperator = "";
        updateDisplay();
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return String.format("%.10f", result)
                    .replaceAll("0*$", "")
                    .replaceAll(" //.$", "");
        }
    }

    // Funções Especiais
    private void onSignButtonClick() {
        if (currentInput.isEmpty() || currentInput.equals("0")) return;
        double value = Double.parseDouble(currentInput);
        value = -value;
        currentInput = formatResult(value);
        updateDisplay();
    }

    private void onPercentButtonClick() {
        if (currentInput.isEmpty()) return;
        double value = Double.parseDouble(currentInput);
        value = value / 100;
        currentInput = formatResult(value);
        updateDisplay();
    }

    private void onClearButtonClick() {
        resetCalculator();
        updateDisplay();
    }

    private void onClearEntryButtonClick() {
        currentInput = "0";
        updateDisplay();
    }

    private void onBackspaceButtonClick() {
        if (currentInput.length() > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
        if (currentInput.isBlank() || currentInput.equals("-")) {
            currentInput = "0";
        }
        updateDisplay();
    }

    private void onReciprocalButtonClick() {
        if (currentInput.isEmpty() || currentInput.equals("0")) return;
        double value = Double.parseDouble(currentInput);
        if (value != 0) {
            value = 1 / value;
            currentInput = formatResult(value);
            updateDisplay();
            resetScreen = true;
        } else {
            display.setText("Error");
        }
    }

    private void onSquareButtonClick() {
        if (currentInput.isEmpty()) return;
        double value = Double.parseDouble(currentInput);
        value = value * value;
        currentInput = formatResult(value);
        updateDisplay();
        resetScreen = true;
    }

    private void onCubeRootButtonClick() {
        if (currentInput.isEmpty()) return;
        double value = Double.parseDouble(currentInput);
        value = Math.cbrt(value);
        currentInput = formatResult(value);
        updateDisplay();
        resetScreen = true;
    }

    // Botões de Memória
    private void onMemoryClear() {
        memory = 0.0;
        display.setText("MC");
    }

    private void onMemoryRecall() {
        currentInput = formatResult(memory);
        updateDisplay();
        resetScreen = true;
    }

    private void onMemoryAdd() {
        try {
            double value = Double.parseDouble(currentInput);
            memory += value;
            display.setText("M+");
        } catch (NumberFormatException e) { }
        resetScreen = true;
    }

    private void onMemorySubtract() {
        try {
            double value = Double.parseDouble(currentInput);
            memory -= value;
            display.setText("M-");
        } catch (NumberFormatException e) { }
        resetScreen = true;
    }

    private void onMemoryStore() {
        try {
            memory = Double.parseDouble(currentInput);
            display.setText("MS");
        } catch (NumberFormatException e) {
            memory = 0.0;
        }
        resetScreen = true;
    }

    private void onMemoryView() { display.setText("M: " + formatResult(memory)); }

    // Auxiliares
    private void resetCalculator() {
        currentInput = "0";
        currentOperator = "";
        operand1 = Double.NaN;
        resetScreen = true;
    }

    private void updateDisplay() {
        display.setText(currentInput.isEmpty() ? "0" : currentInput);
    }
}
