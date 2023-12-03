package com.example.swasthyamitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HealthProfileActivity extends AppCompatActivity {

    ImageButton UserProfileimgbtn, MedicalHistoryimgbtn, FamilyHistoryimgbtn, Allergyimgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_profile);

        UserProfileimgbtn = (ImageButton) findViewById(R.id.UserInfobtn);
        MedicalHistoryimgbtn = (ImageButton) findViewById(R.id.MedicalHistorybtn);
        FamilyHistoryimgbtn = (ImageButton) findViewById(R.id.FamilyHistorybtn);
        Allergyimgbtn = (ImageButton) findViewById(R.id.Allergiesbtn);

        UserProfileimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
                startActivity(intent);
            }
        });


        Allergyimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AllergiesActivity.class);
                startActivity(intent);
            }
        });

    }
}