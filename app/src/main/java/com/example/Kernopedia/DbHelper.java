package com.example.Kernopedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    //Created using Login Register tutorial https://www.youtube.com/watch?v=8obgNNlj3Eo&t=1394
public static final String DBNAME = "UsersData.db";

    public DbHelper(@Nullable Context context) {
        super(context, "UsersData.db", null,1 );
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE users (username TEXT primary key, password TEXT, firstname TEXT, lastname TEXT, email TEXT, birthdate TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE if EXISTS users");

    }
    //add fields from registration form to db
    public Boolean insertData(String username, String password, String firstname, String lastname, String email, String birthdate){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("birthdate", birthdate);
        long result = MyDB.insert("users", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }
    //check if user exists in db
    public Boolean checkusername  (String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount() >0 )
            return true;
        else
            return false;
    }
        //check users credentials against existing db
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        if(cursor.getCount() >0 )
            return true;
        else
            return false;

    }
        //retrieve first name from db
    public Cursor getFirstName(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select firstname from users where username = ? and password = ?", new String[] {username, password});
        if(cursor.getCount() >0 )
            return cursor;
        else
            return null;
    }
}
