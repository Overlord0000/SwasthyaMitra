package com.example.swasthyamitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HealthProfileActivity extends AppCompatActivity {

    ImageButton UserProfileimgbtn, MedicalHistoryimgbtn, FamilyHistoryimgbtn, Allergyimgbtn;
    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_profile);

        DB = new DBhelper(this);

        UserProfileimgbtn = findViewById(R.id.UserInfobtn);
        MedicalHistoryimgbtn = findViewById(R.id.MedicalHistorybtn);
        FamilyHistoryimgbtn = findViewById(R.id.FamilyHistorybtn);
        Allergyimgbtn = findViewById(R.id.Allergiesbtn);

        UserProfileimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DB.checkUserProfileExists()) {
                    // UserProfile exists, redirect to EditUserProfileActivity
                    Intent intent = new Intent(getApplicationContext(), EditUserProfileActivity.class);
                    startActivity(intent);
                } else {
                    // UserProfile doesn't exist, open UserProfileActivity
                    Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        Allergyimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AllergiesActivity.class);
                startActivity(intent);
            }
        });
    }
}
