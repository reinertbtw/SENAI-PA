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
        findViewById(R.id.btn_mr).setOnClickListener(view -> onMemoryReall());
        findViewById(R.id.btn_m_plus).setOnClickListener(view -> onMemoryAdd());
        findViewById(R.id.btn_m_minus).setOnClickListener(view -> onMemorySubtract());
        findViewById(R.id.btn_ms).setOnClickListener(view -> onMemoryStore());|
        findViewById(R.id.btn_mv).setOnClickListener(view -> onMemoryView());
    }
}
