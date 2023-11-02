package com.example.swasthyamitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MedicineManagementActivity extends AppCompatActivity {
    ImageButton Prescription, MedicineReminder, Inventory_Tracker, Stock_Alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_management);

        Prescription = (ImageButton) findViewById(R.id.Prescriptionsbtn);
        MedicineReminder = (ImageButton) findViewById(R.id.MedicineRemindersbtn);
        Inventory_Tracker = (ImageButton) findViewById(R.id.MedicineInventoryTrackerbtn);
        Stock_Alert = (ImageButton) findViewById(R.id.StockAlertbtn);


        Prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PrescriptionActivity.class);
                startActivity(intent);
            }
        });

    }
}