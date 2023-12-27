package com.example.swasthyamitra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    public static final String USER_PROFILE_TABLE = "user_profiles";
    public DBhelper( Context context) {
        super(context,"Login.db", null, 1);
    }

    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_EMERGENCY_CONTACT = "emergency_contact";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BLOOD_GROUP = "blood_group";

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, answer TEXT)");

        MyDB.execSQL("CREATE TABLE " + USER_PROFILE_TABLE + "(" +
                COLUMN_IMAGE + " BLOB," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_AGE + " INTEGER," +
                COLUMN_EMERGENCY_CONTACT + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_GENDER + " TEXT," +
                COLUMN_BLOOD_GROUP + " TEXT" +
                ")");

        MyDB.execSQL("CREATE TABLE allergy_data(" +
                "food INTEGER, " +
                "drug INTEGER, " +
                "environmental INTEGER, " +
                "insect INTEGER, " +
                "severity TEXT, " +
                "allergen TEXT, " +
                "symptoms TEXT, " +
                "triggers TEXT, " +
                "previous_reactions TEXT, " +
                "treatment_plan TEXT, " +
                "medications TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users ");
        MyDB.execSQL("drop Table if exists  USER_PROFILE_TABLE ");
        MyDB.execSQL("drop Table if exists allergy_data");
    }
    public Boolean insertData(String username, String password, String answer){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("answer", answer);



        long result = MyDB.insert("users", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public Boolean updatepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);




        long result = MyDB.update("users",  contentValues, "username = ?", new String[]{username});
        if (result == -1)
            return false;
        else
            return true;

    }


    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if(cursor.getCount() > 0)
            return true;

        else
            return false;

    }

    public Boolean checkanswer(String answer){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where answer = ?", new String[]{answer});
        if(cursor.getCount() > 0)
            return true;

        else
            return false;

    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username = ? and password = ? ", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }


    public void insertUserProfile(String username, int age, String dob, String emergencyContact, String address, String gender, String bloodGroup, String imageUri) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_EMERGENCY_CONTACT, emergencyContact);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_BLOOD_GROUP, bloodGroup);
        values.put(COLUMN_IMAGE, imageUri);

        long newRowId = db.insert(USER_PROFILE_TABLE, null, values);
    }

    public boolean insertAllergyData(boolean food, boolean drug, boolean environmental, boolean insect,
                                     String severity, String allergen, String symptoms,
                                     String triggers, String previousReactions,
                                     String treatmentPlan, String medications) {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("food", food ? 1 : 0);
        contentValues.put("drug", drug ? 1 : 0);
        contentValues.put("environmental", environmental ? 1 : 0);
        contentValues.put("insect", insect ? 1 : 0);
        contentValues.put("severity", severity);
        contentValues.put("allergen", allergen);
        contentValues.put("symptoms", symptoms);
        contentValues.put("triggers", triggers);
        contentValues.put("previous_reactions", previousReactions);
        contentValues.put("treatment_plan", treatmentPlan);
        contentValues.put("medications", medications);

        long result = MyDB.insert("allergy_data", null, contentValues);

        if (result == -1) {

            Log.e("DB_ERROR", "Failed to insert data into allergy_data");
        }

        return result != -1;
    }

    public boolean checkUserProfileExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_PROFILE_TABLE, null);

        boolean profileExists = cursor.moveToFirst();

        cursor.close();
        return profileExists;
    }
}
