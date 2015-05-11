package com.app.blog.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by darknoe on 15/4/15.
 */
public class DataProvider extends ContentProvider {
    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (Routes.resolveRoute(uri)) {
            case Routes._POSTS: {
                long _id = 0;
                if(values != null) {
                    _id = db.insert(DataContract.PostEntry.TABLE_NAME, null, values);
                }
                else{

                }
                if ( _id > 0 )
                    returnUri = DataContract.PostEntry.buildPostUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (Routes.resolveRoute(uri)) {
            case Routes._POSTS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        if(value != null){
                            long _id = db.insert(DataContract.PostEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnCount++;
                            }
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;

        switch (Routes.resolveRoute(uri)) {
            case Routes._POSTS:
                rowsUpdated = db.update(DataContract.PostEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (Routes.resolveRoute(uri)) {
            case Routes._POSTS:
                rowsDeleted = db.delete(DataContract.PostEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (Routes.resolveRoute(uri)) {
            case Routes._POSTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.PostEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case Routes._POSTS_BY_ID: {
                String idPost = DataContract.PostEntry.getIdPost(uri);
                String[] mySelectionArgs = {idPost};

                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.PostEntry.TABLE_NAME,
                        projection,
                        DataContract.PostEntry.COLUMN_ID + " = ?",
                        mySelectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;
            }

            case Routes._FIRST_POST: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.PostEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        DataContract.PostEntry.COLUMN_ID + " DESC LIMIT 1"
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.

        switch (Routes.resolveRoute(uri)) {
            case Routes._POSTS:
                return DataContract.PostEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

}
