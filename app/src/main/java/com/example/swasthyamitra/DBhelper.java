package com.example.swasthyamitra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users ");
        MyDB.execSQL("drop Table if exists  USER_PROFILE_TABLE ");

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
}
