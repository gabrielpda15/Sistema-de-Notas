package com.example.sistemadenotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    float p1, p2, p3, average;
    String result;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView txtDiscipline = findViewById(R.id.txtDiscipline);
        TextView txtStudent = findViewById(R.id.txtStudent);
        TextView txtAbsence = findViewById(R.id.txtAbsence);

        Button btnShare = findViewById(R.id.btnShareSubmit);

        Intent intent = getIntent();
        p1 = intent.getFloatExtra("p1", 0);
        p2 = intent.getFloatExtra("p2", 0);
        p3 = intent.getFloatExtra("p3", 0);
        average = intent.getFloatExtra("average", 0);
        result = intent.getStringExtra("result");

        btnShare.setEnabled(false);

        View.OnFocusChangeListener focusListener = (view, hasFocus) -> {
            if (!hasFocus) {
                String disciplineValue = txtDiscipline.getText().toString();
                String studentValue = txtStudent.getText().toString();

                if (!isStringEmpty(disciplineValue) && !isStringEmpty(studentValue)) {
                    btnShare.setEnabled(true);
                }
            }
        };

        txtDiscipline.setOnFocusChangeListener(focusListener);
        txtStudent.setOnFocusChangeListener(focusListener);
        txtAbsence.setOnFocusChangeListener(focusListener);

        btnShare.setOnClickListener(view -> {
            String disciplineValue = txtDiscipline.getText().toString();
            String studentValue = txtStudent.getText().toString();
            int absenceValue = tryParseInt(txtAbsence.getText().toString());

            String shareString = getString(R.string.share_string)
                    .replace("{name}", studentValue)
                    .replace("{result}", result)
                    .replace("{average}", Float.toString(average))
                    .replace("{discipline}", disciplineValue)
                    .replace("{p1}", Float.toString(p1))
                    .replace("{p2}", Float.toString(p2))
                    .replace("{p3}", Float.toString(p3))
                    .replace("{absence}", Integer.toString(absenceValue));

            String shareSubject = getString(R.string.share_subject)
                    .replace("{discipline}", disciplineValue);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareString);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            sendIntent.setType("text/plain");

            startActivity(sendIntent);
        });
        
    }

    private boolean isStringEmpty(String str) {
        String value = str != null ? str : "";
        value = value.trim().replaceAll("/\\s*/g", "");
        return value.isEmpty();
    }

    private int tryParseInt(String str) {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

}