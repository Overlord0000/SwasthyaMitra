package com.example.swasthyamitra;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Calendar;

public class UserProfileActivity extends AppCompatActivity {

    ImageView ProfilePic;
    EditText EdName, EdAge, EdDOb, EdEmergencyContact, EdAddress;
    RadioButton btnMale, btnFemale;
    Spinner SpinBloodGroup;
    Button btnSave;

    String[] bloodGroups;
    DBhelper DB;

    private Uri userProfilePictureUri;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        bloodGroups = getResources().getStringArray(R.array.blood_groups);

        ProfilePic = (ImageView) findViewById(R.id.ProfilePicture);
        EdName = (EditText) findViewById(R.id.EditName);
        EdAge = (EditText) findViewById(R.id.EditAge);
        EdDOb = (EditText) findViewById(R.id.EditDOB);
        EdEmergencyContact = (EditText) findViewById(R.id.EditEmergencyContact);
        EdAddress = (EditText) findViewById(R.id.EditAddress);
        SpinBloodGroup = (Spinner) findViewById(R.id.spinnerBloodGroup);
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnMale = (RadioButton) findViewById(R.id.radioButtonMale);
        btnFemale = (RadioButton) findViewById(R.id.radioButtonFemale);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinBloodGroup.setAdapter(adapter);

        DB = new DBhelper(this);

        ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooserDialog();
            }
        });

        EdDOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String userName = EdName.getText().toString();
                int userAge = Integer.parseInt(EdAge.getText().toString());
                String userDateOfBirth = EdDOb.getText().toString();
                String userEmergencyContact = EdEmergencyContact.getText().toString();
                String userAddress = EdAddress.getText().toString();
                String userGender = btnMale.isChecked() ? "Male" : (btnFemale.isChecked() ? "Female" : "");
                String userBloodGroup = SpinBloodGroup.getSelectedItem().toString();


                if (userProfilePictureUri != null) {

                  DB.insertUserProfile(userName, userAge, userDateOfBirth, userEmergencyContact, userAddress, userGender, userBloodGroup, userProfilePictureUri.toString());
                  Toast.makeText(UserProfileActivity.this, "User profile saved", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(UserProfileActivity.this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showImageChooserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");

        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    openCamera();
                    break;
                case 1:
                    openGallery();
                    break;
            }
        });

        builder.show();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                userProfilePictureUri = null; //reset uri
                Glide.with(this)
                        .load(imageBitmap)
                        .apply(new RequestOptions()
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true))
                        .into(ProfilePic);
            } else if (requestCode == REQUEST_IMAGE_PICK) {

                userProfilePictureUri = data.getData();
                Glide.with(this)
                        .load(userProfilePictureUri)
                        .apply(new RequestOptions()
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true))
                        .into(ProfilePic);
            }
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, dateSetListener, year, month, day
        );

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String selectedDate = String.format("%02d/%02d/%02d", dayOfMonth, month + 1, year % 100);
            EdDOb.setText(selectedDate);
        }
    };
}
