package com.myproject.githubuser3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.myproject.githubuser3.model.UserGithub;
import java.util.ArrayList;

import static com.myproject.githubuser3.db.UserDbContract.UserColumns.AVATAR;
import static com.myproject.githubuser3.db.UserDbContract.UserColumns.ID;
import static com.myproject.githubuser3.db.UserDbContract.UserColumns.TABLE_USER_NAME;
import static com.myproject.githubuser3.db.UserDbContract.UserColumns.URL;
import static com.myproject.githubuser3.db.UserDbContract.UserColumns.USERNAME;

public class UserHelper {

    private static final String DATABASE_TABLE = TABLE_USER_NAME;
    private static UserDbHelper userDbHelper;
    private static UserHelper INST;
    private static SQLiteDatabase database;

    public UserHelper(Context c) {

        userDbHelper = new UserDbHelper(c);

    }

    public static UserHelper getInstance(Context c) {

        if (INST == null) {

            synchronized (SQLiteOpenHelper.class) {

                if (INST == null) {
                    INST = new UserHelper(c);

                }

            }

        }

        return INST;

    }

    public void open() throws SQLException {

        database = userDbHelper.getWritableDatabase();

    }

    public void close() {

        userDbHelper.close();
        if (database.isOpen())
            database.close();

    }

    public Cursor queryAll() {

        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                ID+ " ASC");

    }

    public Cursor queryById(String id) {

        return database.query(DATABASE_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);

    }

    public ArrayList<UserGithub> getDataUser(){

        ArrayList<UserGithub> userGithubList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,
                null,
                null,
                null,
                null,
                USERNAME + " ASC",
                null);
        cursor.moveToFirst();
        UserGithub userGithub;

        if (cursor.getCount() > 0){

            do {
                userGithub = new UserGithub();
                userGithub.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                userGithub.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(USERNAME)));
                userGithub.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR)));
                userGithub.setHtmlUrl(cursor.getString(cursor.getColumnIndexOrThrow(URL)));
                userGithubList.add(userGithub);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }cursor.close();
        return userGithubList;

    }

    public long userInsert(UserGithub userGithub){

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,userGithub.getId());
        contentValues.put(USERNAME,userGithub.getLogin());
        contentValues.put(URL,userGithub.getHtmlUrl());
        contentValues.put(AVATAR,userGithub.getAvatarUrl());

        return database.insert(DATABASE_TABLE,null, contentValues);

    }

    public int userDelete(String id){

        return database.delete(TABLE_USER_NAME,ID + " = '" + id + "'", null);

    }

    public int DeleteProvider(String id) {

        return database.delete(TABLE_USER_NAME, ID+ "=?",new String[]{id});

    }
    public int UpdateProvider(String id, ContentValues values) {

        return database.update(DATABASE_TABLE, values, ID + " =?", new String[]{id});

    }
    public long InsertProvider(ContentValues values) {

        return database.insert(DATABASE_TABLE, null, values);

    }
}

