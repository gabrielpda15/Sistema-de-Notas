package com.example.sistemadenotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private float p1, p2, p3, average;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtP1 = findViewById(R.id.txtP1);
        TextView txtP2 = findViewById(R.id.txtP2);
        TextView txtP3 = findViewById(R.id.txtP3);
        TextView txtResult = findViewById(R.id.txtResult);

        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnShare = findViewById(R.id.btnShare);

        btnCalc.setOnClickListener(view -> {
            p1 = tryParseFloat(txtP1.getText().toString());
            p2 = tryParseFloat(txtP2.getText().toString());
            p3 = tryParseFloat(txtP3.getText().toString());
            average = getAverage(p1, p2);

            if (average < 6) {
                if (p1 < p2 && p1 < p3) {
                    average = getAverage(p3, p2);
                } else if (p2 < p3) {
                    average = getAverage(p1, p3);
                }
            }

            result = average >= 6 ? getString(R.string.approved) : getString(R.string.reproved);

            String resultText = getString(R.string.result)
                    .replace("{average}", String.valueOf(average))
                    .replace("{result}", result);

            txtResult.setText(resultText);
            btnShare.setVisibility(View.VISIBLE);
        });

        btnShare.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShareActivity.class);
            intent.putExtra("p1", p1);
            intent.putExtra("p2", p2);
            intent.putExtra("p3", p3);
            intent.putExtra("average", average);
            intent.putExtra("result", result);

            startActivity(intent);
        });
    }

    private float getAverage(float n1, float n2) {
        return ((n1 * 2) + (n2 * 3)) / 5;
    }

    private float tryParseFloat(String str) {
        try {
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

}