package com.app.blog.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by darknoe on 16/4/15.
 */
public class DataAuthenticatorService extends Service {
    private DataAuthenticator authenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        authenticator = new DataAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
