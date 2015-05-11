package com.app.blog.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by darknoe on 15/4/15.
 */
public class DataContract {
    public static final String CONTENT_AUTHORITY = "com.kiory.blog";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_POST = "post";

    public static final class PostEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POST).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POST;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POST;

        public static final String TABLE_NAME = "post";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SLUG = "slug";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TITLE_PLAIN = "title_plain";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_EXCERPT = "excerpt";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_HEADER = "header";

        public static Uri buildPostUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildFirstPostUri() {
            return CONTENT_URI.buildUpon().appendPath("first").build();
        }

        public static String getIdPost(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

}
