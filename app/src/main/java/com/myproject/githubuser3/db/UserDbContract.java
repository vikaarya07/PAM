package com.myproject.githubuser3.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserDbContract {

    public static final String AUTHORITY = "com.myproject.githubuser3";
    public static final String SCHEME = "content";

    public static final class UserColumns implements BaseColumns {

        public static final String TABLE_USER_NAME = "user";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        static final String URL = "url";
        static final String AVATAR = "avatar";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_USER_NAME)
                .build();

    }
}
