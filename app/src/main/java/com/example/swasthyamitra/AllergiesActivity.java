package com.example.swasthyamitra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AllergiesActivity extends AppCompatActivity {

    private CheckBox checkBoxFood, checkBoxDrug, checkBoxEnvironmental, checkBoxInsect;
    private Spinner spinnerSeverity;
    private EditText editTextAllergen, editTextSymptoms, editTextTriggers,
            editTextPreviousReactions, editTextTreatmentPlan, editTextMedications;
    private Button buttonSaveAllergy;

    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies);


        checkBoxFood = findViewById(R.id.checkBoxFood);
        checkBoxDrug = findViewById(R.id.checkBoxDrug);
        checkBoxEnvironmental = findViewById(R.id.checkBoxEnvironmental);
        checkBoxInsect = findViewById(R.id.checkBoxInsect);
        spinnerSeverity = findViewById(R.id.spinnerSeverity);
        editTextAllergen = findViewById(R.id.editTextAllergen);
        editTextSymptoms = findViewById(R.id.editTextSymptoms);
        editTextTriggers = findViewById(R.id.editTextTriggers);
        editTextPreviousReactions = findViewById(R.id.editTextPreviousReactions);
        editTextTreatmentPlan = findViewById(R.id.editTextTreatmentPlan);
        editTextMedications = findViewById(R.id.editTextMedications);
        buttonSaveAllergy = findViewById(R.id.buttonSaveAllergy);

        DB = new DBhelper(this);
        buttonSaveAllergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveAllergyData();
            }
        });
    }

    private void saveAllergyData() {

        boolean foodSelected = checkBoxFood.isChecked();
        boolean drugSelected = checkBoxDrug.isChecked();
        boolean environmentalSelected = checkBoxEnvironmental.isChecked();
        boolean insectSelected = checkBoxInsect.isChecked();

        String selectedSeverity = spinnerSeverity.getSelectedItem().toString();

        String enteredAllergen = editTextAllergen.getText().toString();
        String enteredSymptoms = editTextSymptoms.getText().toString();
        String enteredTriggers = editTextTriggers.getText().toString();
        String enteredPreviousReactions = editTextPreviousReactions.getText().toString();
        String enteredTreatmentPlan = editTextTreatmentPlan.getText().toString();
        String enteredMedications = editTextMedications.getText().toString();

        boolean isSaved = DB.insertAllergyData(
                foodSelected, drugSelected, environmentalSelected, insectSelected,
                selectedSeverity, enteredAllergen, enteredSymptoms, enteredTriggers, enteredPreviousReactions,
                enteredTreatmentPlan,enteredMedications
        );

        if (isSaved) {
            Toast.makeText(this, "saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "failed to save data", Toast.LENGTH_SHORT).show();
        }
    }
    }

