package com.example.swasthyamitra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserProfileActivity extends AppCompatActivity {

    private ImageView ProfilePic;
    private EditText EdName, EdAge, EdDOb, EdEmergencyContact, EdAddress;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Spinner spinnerBloodGroup;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        ProfilePic = findViewById(R.id.ProfilePicture);
        EdName = findViewById(R.id.EditName);
        EdAge = findViewById(R.id.EditAge);
        EdDOb = findViewById(R.id.EditDOB);
        EdEmergencyContact = findViewById(R.id.EditEmergencyContact);
        EdAddress = findViewById(R.id.EditAddress);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // Add your code for initializing the Spinner (spinnerBloodGroup) with data if needed

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code here to handle the update operation
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        // Retrieve data from UI elements
        String userName = EdName.getText().toString();
        int userAge = Integer.parseInt(EdAge.getText().toString());
        String userDateOfBirth = EdDOb.getText().toString();
        String userEmergencyContact = EdEmergencyContact.getText().toString();
        String userAddress = EdAddress.getText().toString();
        String userGender = radioButtonMale.isChecked() ? "Male" : (radioButtonFemale.isChecked() ? "Female" : "");
        String userBloodGroup = spinnerBloodGroup.getSelectedItem().toString();

        // Add your code here to perform the update operation in the database

        // Display a toast message indicating that the profile has been updated
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
}
