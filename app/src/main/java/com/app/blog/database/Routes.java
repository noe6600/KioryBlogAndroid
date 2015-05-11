package com.app.blog.database;

import android.content.UriMatcher;
import android.net.Uri;

/**
 * Created by darknoe on 15/4/15.
 */
public class Routes {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    /**
     * Route: content://com.app.tecnomarqueting/posts
     */
    public static final int _POSTS = 100;
    public static final int _POSTS_BY_ID = 101;
    public static final int _FIRST_POST = 102;

    static{
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_POST, _POSTS);
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_POST+"/#", _POSTS_BY_ID);
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_POST+"/*", _FIRST_POST);
    };

    public static int resolveRoute(Uri u){
        return sUriMatcher.match(u);
    }
}
