package com.example.swasthyamitra;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    ImageButton HealthProfile, MedicationManagement, DoctorsAppointments, EmergencyTools, Wellness, Tracking, LogOut;


    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HealthProfile = findViewById(R.id.HealthProfilebtn);
        MedicationManagement = findViewById(R.id.MedicicineManagebtn);
        DoctorsAppointments = findViewById(R.id.MedicicineManagebtn);
        EmergencyTools = findViewById(R.id.EmergencyToolsbtn);
        Wellness = findViewById(R.id.Wellnessbtn);
        Tracking = findViewById(R.id.Trackingbtn);
        LogOut = findViewById(R.id.btnLogout);

        HealthProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HealthProfileActivity.class);
                startActivity(intent);
            }
        });

        MedicationManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicineManagementActivity.class);
                startActivity(intent);
            }
        });

        Wellness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WellnessActivity.class);
                startActivity(intent);
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGGED_IN, false);
        editor.apply();


        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
