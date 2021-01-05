package com.myproject.githubuser3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.myproject.githubuser3.db.UserDbContract.UserColumns.TABLE_USER_NAME;

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String USER_DB_NAME = "userdbname";
    private static final int USER_DB_VERSION = 2;

    private static final String SQL_CREATE_TABLE_USER = String.format(
            "CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            TABLE_USER_NAME,
            UserDbContract.UserColumns.ID,
            UserDbContract.UserColumns.USERNAME,
            UserDbContract.UserColumns.URL,
            UserDbContract.UserColumns.AVATAR

    );

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        onCreate(db);

    }

    public UserDbHelper(Context c){

        super(c,USER_DB_NAME,null,USER_DB_VERSION);

    }

}
