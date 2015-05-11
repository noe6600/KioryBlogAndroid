package com.app.blog.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import com.app.blog.database.DataContract;
import com.app.blog.datastructure.Blog;
import com.app.blog.utils.JsonParser;
import com.app.blog.utils.Network;
import com.kiory.blog.R;

//import com.app.blog.R;

/**
 * Created by darknoe on 15/4/15.
 */
public class DataSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    ContentResolver mContentResolver;
    private static Context context;

    public DataSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Blog blog = new Blog();
        blog = (Blog) JsonParser.parse(blog, Network.get(context.getString(R.string.url_base_path)));
        insertPosts(blog);
    }

    public void insertPosts(Blog blog){
        if(blog.getPosts() != null) {
            ContentValues[] values = new ContentValues[blog.getPosts().size() + 1];
            String icon = "http://www.tecnomarqueting.com/novaweb/wp-content/uploads/2015/04/Logo-TecnoMarqueting-NovaWeb.jpg";
            String header = "http://www.tecnomarqueting.com/novaweb/wp-content/uploads/2015/04/Logo-TecnoMarqueting-NovaWeb.jpg";

            for (int i = 0; i < blog.getPosts().size(); i++) {
                if (blog.getPosts().get(i).getThumbnail_images() != null) {
                    if (blog.getPosts().get(i).getThumbnail_images().getBlog_square() != null) {
                        icon = blog.getPosts().get(i).getThumbnail_images().getBlog_square().getUrl();
                    }
                    if (blog.getPosts().get(i).getThumbnail_images().getThumbnail() != null) {
                        icon = blog.getPosts().get(i).getThumbnail_images().getThumbnail().getUrl();
                    }
                }
                if (blog.getPosts().get(i).getThumbnail_images() != null) {
                    if (blog.getPosts().get(i).getThumbnail_images().getFull() != null) {
                        header = blog.getPosts().get(i).getThumbnail_images().getFull().getUrl();
                    }
                }

                values[i] = setPetValues(blog.getPosts().get(i).getId(), blog.getPosts().get(i).getTitle(), blog.getPosts().get(i).getSlug(),
                        blog.getPosts().get(i).getType(), blog.getPosts().get(i).getUrl(), blog.getPosts().get(i).getStatus(),
                        blog.getPosts().get(i).getTitle_plain(), blog.getPosts().get(i).getExcerpt(), blog.getPosts().get(i).getDate(),
                        blog.getPosts().get(i).getContent(), icon, header);
            }

            mContentResolver.bulkInsert(DataContract.PostEntry.CONTENT_URI, values);
        }
    }

    private ContentValues setPetValues(int id, String name, String slug, String type, String url, String status, String title_plain, String excerpt, String date, String content, String icon, String header) {
        ContentValues values = new ContentValues();
        values.put(DataContract.PostEntry.COLUMN_ID, id);
        values.put(DataContract.PostEntry.COLUMN_TITLE, name);
        values.put(DataContract.PostEntry.COLUMN_SLUG, slug);
        values.put(DataContract.PostEntry.COLUMN_TYPE, type);
        values.put(DataContract.PostEntry.COLUMN_URL, url);
        values.put(DataContract.PostEntry.COLUMN_STATUS, status);
        values.put(DataContract.PostEntry.COLUMN_TITLE_PLAIN, title_plain);
        values.put(DataContract.PostEntry.COLUMN_EXCERPT, excerpt);
        values.put(DataContract.PostEntry.COLUMN_DATE, date);
        values.put(DataContract.PostEntry.COLUMN_CONTENT, content);
        values.put(DataContract.PostEntry.COLUMN_ICON, icon);
        values.put(DataContract.PostEntry.COLUMN_HEADER, header);

        return values;
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        // Create the account type and default account

        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }

        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        DataSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

}
