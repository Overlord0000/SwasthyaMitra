package com.example.swasthyamitra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private EditText edName, edAge, edDob, edEmergencyContact, edAddress;
    private RadioButton btnMale, btnFemale;
    private Spinner spinBloodGroup;
    private Button btnSave;

    private String[] bloodGroups;
    private DBhelper dbHelper;

    private Uri userProfilePictureUri;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        bloodGroups = getResources().getStringArray(R.array.blood_groups);

        profilePic = findViewById(R.id.ProfilePicture);
        edName = findViewById(R.id.EditName);
        edAge = findViewById(R.id.EditAge);
        edDob = findViewById(R.id.EditDOB);
        edEmergencyContact = findViewById(R.id.EditEmergencyContact);
        edAddress = findViewById(R.id.EditAddress);
        spinBloodGroup = findViewById(R.id.spinnerBloodGroup);
        btnSave = findViewById(R.id.buttonSave);
        btnMale = findViewById(R.id.radioButtonMale);
        btnFemale = findViewById(R.id.radioButtonFemale);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBloodGroup.setAdapter(adapter);

        dbHelper = new DBhelper(this);

        profilePic.setOnClickListener(v -> showImageChooserDialog());

        edDob.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> saveUserProfile());
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
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    userProfilePictureUri = null; // reset uri
                    loadImageIntoImageView(imageBitmap);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                // Handle the selected image from the gallery
                if (data != null && data.getData() != null) {
                    userProfilePictureUri = data.getData();
                    loadImageIntoImageView(userProfilePictureUri);
                }
            }
        }
    }

    private void loadImageIntoImageView(Bitmap bitmap) {
        Glide.with(this)
                .load(bitmap)
                .apply(new RequestOptions()
                        .centerCrop()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(profilePic);
    }

    private void loadImageIntoImageView(Uri uri) {
        Glide.with(this)
                .load(uri)
                .apply(new RequestOptions()
                        .centerCrop()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(profilePic);
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

    private final DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
        String selectedDate = String.format("%02d/%02d/%02d", dayOfMonth, month + 1, year % 100);
        edDob.setText(selectedDate);
    };

    private void saveUserProfile() {
        String userName = edName.getText().toString();
        int userAge = Integer.parseInt(edAge.getText().toString());
        String userDateOfBirth = edDob.getText().toString();
        String userEmergencyContact = edEmergencyContact.getText().toString();
        String userAddress = edAddress.getText().toString();
        String userGender = btnMale.isChecked() ? "Male" : (btnFemale.isChecked() ? "Female" : "");
        String userBloodGroup = spinBloodGroup.getSelectedItem().toString();

        if (userProfilePictureUri != null) {
            dbHelper.insertUserProfile(userName, userAge, userDateOfBirth, userEmergencyContact, userAddress, userGender, userBloodGroup, userProfilePictureUri.toString());
            Toast.makeText(UserProfileActivity.this, "User profile saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UserProfileActivity.this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
        }
    }
}
