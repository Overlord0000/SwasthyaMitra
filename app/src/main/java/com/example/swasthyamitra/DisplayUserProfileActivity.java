package com.example.swasthyamitra;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.Objects;

public class DisplayUserProfileActivity extends AppCompatActivity {

    ImageView DisplayProfilePic;
    EditText DisplayEdName, DisplayEdAge, DisplayEdDOb, DisplayEdEmergencyContact, DisplayEdAddress;
    RadioButton DisplaybtnMale, DisplaybtnFemale;
    Spinner DisplaySpinBloodGroup;
    RadioGroup DisplayradioGroupGender;
    Button DisplaybtnSave, DisplaybtnUpdate;

    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_profile);

        DisplayProfilePic = findViewById(R.id.DisplayProfilePicture);
        DisplayEdName = findViewById(R.id.DisplayEditName);
        DisplayEdAge = findViewById(R.id.DisplayEditAge);
        DisplayEdDOb = findViewById(R.id.DisplayEditDOB);
        DisplayEdEmergencyContact = findViewById(R.id.DisplayEditEmergencyContact);
        DisplayEdAddress = findViewById(R.id.DisplayEditAddress);
        DisplaySpinBloodGroup = findViewById(R.id.DisplayspinnerBloodGroup);
        DisplayradioGroupGender = findViewById(R.id.DisplayradioGroupGender);

        DisplaybtnUpdate = findViewById(R.id.DisplaybuttonUpdate);

        DB = new DBhelper(this);

        // Fetch user profile data from the database
        Cursor cursor = DB.getUserProfileData(); // Implement this method in your DBHelper class

        if (cursor != null && cursor.moveToFirst()) {
            // Populate UI elements with data from the cursor
            String userName = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_USERNAME));
            int userAge = cursor.getInt(cursor.getColumnIndex(DBhelper.COLUMN_AGE));
            String userDateOfBirth = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_DOB));
            String userEmergencyContact = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_EMERGENCY_CONTACT));
            String userAddress = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_ADDRESS));
            String userGender = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_GENDER));
            String userBloodGroup = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_BLOOD_GROUP));
            String userProfilePictureUri = cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_IMAGE));

            // Set data to UI elements
            DisplayEdName.setText(userName);
            DisplayEdAge.setText(String.valueOf(userAge));
            DisplayEdDOb.setText(userDateOfBirth);
            DisplayEdEmergencyContact.setText(userEmergencyContact);
            DisplayEdAddress.setText(userAddress);

            // Set gender
            if (Objects.equals(userGender, "Male")) {
                DisplayradioGroupGender.check(R.id.DisplayradioButtonMale);
            } else if (Objects.equals(userGender, "Female")) {
                DisplayradioGroupGender.check(R.id.DisplayradioButtonFemale);
            }

            // Set blood group
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) DisplaySpinBloodGroup.getAdapter();
            if (adapter != null) {
                int position = adapter.getPosition(userBloodGroup);
                DisplaySpinBloodGroup.setSelection(position);
            }

            // Load and display profile picture using Glide
            Glide.with(this)
                    .load(userProfilePictureUri)
                    .apply(new RequestOptions()
                            .centerCrop()
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(DisplayProfilePic);
        }

        // Add onClickListener for Update button if you want to implement an update functionality
        DisplaybtnUpdate.setOnClickListener(v -> {
            // Implement update functionality here
        });
    }
}
