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


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class UserProfileActivity extends AppCompatActivity {

    ImageView ProfilePic;

    EditText EdName, EdAge, EdDOb, EdEmergencyContact, EdAddress;

    RadioButton btnMale, btnFemale;

    Spinner SpinBloodGroup;

    Button btnSave;

    String[] bloodGroups;

    DBhelper DB;

    boolean isEditMode = false;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        bloodGroups  = getResources().getStringArray(R.array.blood_groups);

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


        ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooserDialog();
            }
        });


    }

    private void showImageChooserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");

        // Add options for camera and gallery
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Open the camera
                    openCamera();
                    break;
                case 1:
                    // Open the gallery
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
                // Handle the captured image from the camera
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ProfilePic.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                // Handle the selected image from the gallery
                Uri selectedImageUri = data.getData();

                Glide.with(this)
                        .load(selectedImageUri)
                        .apply(new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true))
                                .into(ProfilePic);
            }
        }
    }
    public void showDatePicker(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Format the selected date as DD/MM/YY
                        String selectedDate = String.format("%02d/%02d/%02d", dayOfMonth, month + 1, year % 100);
                        EdDOb.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }


}
