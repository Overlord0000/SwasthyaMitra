package com.example.swasthyamitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    ImageButton HealthProfile, MedicationManagement, DoctorsAppointments, EmergencyTools, Wellness, Tracking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HealthProfile = (ImageButton) findViewById(R.id.HealthProfilebtn);
        MedicationManagement = (ImageButton) findViewById(R.id.MedicicineManagebtn);
        DoctorsAppointments = (ImageButton) findViewById(R.id.MedicicineManagebtn);
        EmergencyTools = (ImageButton) findViewById(R.id.EmergencyToolsbtn);
        Wellness = (ImageButton) findViewById(R.id.Wellnessbtn);
        Tracking = (ImageButton) findViewById(R.id.Trackingbtn);


        HealthProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HealthProfileActivity.class);
                startActivity(intent);
            }
        });

        MedicationManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PrescriptionActivity.class);
                startActivity(intent);
            }
        });

        Wellness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),WellnessActivity.class);
                startActivity(intent);

            }
        });




    }
}