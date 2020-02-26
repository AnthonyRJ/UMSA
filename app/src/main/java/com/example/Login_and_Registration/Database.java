package com.example.Login_and_Registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="UMSA.db";
    public static final String TABLE_NAME="USER";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Requetes qui vont s'executer à la création de la base
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
    }

    //Requetes qui vont s'executer à chaque mise a jour de la base
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Ajout d'un utilisateur dans la base de donnée
    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVal = new ContentValues();

        contentVal.put("username", user);
        contentVal.put("password", password);

        long result = db.insert("USER", null, contentVal);
        db.close();

        return result;
    }

    //Verification si un utilisateur est deja inscrit ou pas
    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();

        String select = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,select,selectArgs, null, null, null);

        int counter = cursor.getCount();
        cursor.close();
        db.close();

        if(counter > 0)
            return true;
        else
            return false;
    }
}
