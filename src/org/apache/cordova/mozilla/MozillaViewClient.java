package org.apache.cordova.mozilla;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;

import android.util.Log;

public class MozillaViewClient implements CordovaWebViewClient {

    String TAG = "MozillaViewClient";
    CordovaWebView view;
    
    @Override
    public void setWebView(CordovaWebView appView) {
        Log.d(TAG, "This actually does nothing but save the appView");
        view = appView;
    }

    @Override
    public void onReceivedError(CordovaWebView me, int i, String string,
            String url) {
        Log.d(TAG, "Something went wrong! In Gecko we don't go here!");
    }

}
