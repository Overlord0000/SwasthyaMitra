package com.example.swasthyamitra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;



public class AllergiesActivity extends AppCompatActivity {

    Spinner spinnerAllergyType, spinnerSeverity;
    Button buttonSaveAllergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies);

        spinnerAllergyType = findViewById(R.id.spinnerAllergyType);
        spinnerSeverity = findViewById(R.id.spinnerSeverity);
        buttonSaveAllergy = findViewById(R.id.buttonSaveAllergy);


        ArrayAdapter<CharSequence> allergyTypeAdapter = ArrayAdapter.createFromResource(this, R.array.allergy_types, android.R.layout.simple_spinner_item);
        allergyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAllergyType.setAdapter(allergyTypeAdapter);

        ArrayAdapter<CharSequence> severityAdapter = ArrayAdapter.createFromResource(this, R.array.severity_levels, android.R.layout.simple_spinner_item);
        severityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeverity.setAdapter(severityAdapter);


        buttonSaveAllergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedAllergyType = spinnerAllergyType.getSelectedItem().toString();
                String selectedSeverity = spinnerSeverity.getSelectedItem().toString();


            }
        });
    }
}
