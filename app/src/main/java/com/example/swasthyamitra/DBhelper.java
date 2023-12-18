package com.example.swasthyamitra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    public static final String USER_PROFILE_TABLE = "user_profiles";

    // Columns for the user_profiles table
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_EMERGENCY_CONTACT = "emergency_contact";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BLOOD_GROUP = "blood_group";

    public DBhelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        // Create users table
        MyDB.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT, answer TEXT)");

        // Create user_profiles table
        MyDB.execSQL("CREATE TABLE " + USER_PROFILE_TABLE + "(" +
                COLUMN_IMAGE + " BLOB," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_AGE + " INTEGER," +
                COLUMN_EMERGENCY_CONTACT + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_GENDER + " TEXT," +
                COLUMN_BLOOD_GROUP + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS " + USER_PROFILE_TABLE);
        onCreate(MyDB);
    }

    public Boolean insertData(String username, String password, String answer) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("answer", answer);

        long result = MyDB.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean updatePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);

        long result = MyDB.update("users", contentValues, "username = ?", new String[]{username});
        return result != -1;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Boolean checkAnswer(String answer) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE answer = ?", new String[]{answer});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
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

        db.insert(USER_PROFILE_TABLE, null, values);
    }
    public Cursor getUserProfileData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                USER_PROFILE_TABLE,  // The table to query
                null,                // The columns to return (null for all columns)
                null,                // The columns for the WHERE clause
                null,                // The values for the WHERE clause
                null,                // Don't group the rows
                null,                // Don't filter by row groups
                null                 // The sort order
        );
    }
}
