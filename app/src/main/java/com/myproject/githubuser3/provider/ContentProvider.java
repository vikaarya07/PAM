package com.myproject.githubuser3.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.myproject.githubuser3.db.UserHelper;
import java.util.Objects;

import static com.myproject.githubuser3.db.UserDbContract.AUTHORITY;
import static com.myproject.githubuser3.db.UserDbContract.UserColumns.CONTENT_URI;
import static com.myproject.githubuser3.db.UserDbContract.UserColumns.TABLE_USER_NAME;

public class ContentProvider extends android.content.ContentProvider {

    private static final int USER = 0;
    private static final int USER_ID = 1;
    UserHelper userHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(AUTHORITY,TABLE_USER_NAME,USER);
        uriMatcher.addURI(AUTHORITY,TABLE_USER_NAME + "/#",USER_ID);

    }
    @Override
    public boolean onCreate() {

        userHelper = UserHelper.getInstance(getContext());
        userHelper.open();
        return true;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (uriMatcher.match(uri)){

            case USER:
                cursor = userHelper.queryAll();
                break;
            case USER_ID:
                cursor = userHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;

        }

        if (cursor != null){

            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);

        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        return null;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long added;
        Uri contentUri = null;
        if (uriMatcher.match(uri) == USER) {
            added = userHelper.InsertProvider(values);
            if (added > 0) {
                contentUri = ContentUris.withAppendedId(CONTENT_URI, added);
            }
        } else {
            added = 0;
        }

        if (added > 0) {

            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

        }

        return contentUri;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int update;
        if (uriMatcher.match(uri) == USER_ID) {
            update = userHelper.UpdateProvider(uri.getLastPathSegment(), values);
        } else {
            update = 0;
        }

        if (update > 0) {

            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

        }

        return update;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int delete;
        if (uriMatcher.match(uri) == USER_ID) {
            delete = userHelper.DeleteProvider(uri.getLastPathSegment());
        } else {
            delete = 0;
        }

        if (delete > 0) {

            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

        }

        return delete;
    }
}
