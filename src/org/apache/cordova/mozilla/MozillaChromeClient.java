package org.apache.cordova.mozilla;

import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;

import android.net.Uri;
import android.webkit.ValueCallback;

public class MozillaChromeClient implements CordovaChromeClient {

    CordovaWebView view;
    
    @Override
    public void setWebView(CordovaWebView appView) {
        view = appView;
    }

    /*
     * What the fuck does this do?
     * (non-Javadoc)
     * @see org.apache.cordova.CordovaChromeClient#getValueCallback()
     */
    
    @Override
    public ValueCallback<Uri> getValueCallback() {
        // TODO Auto-generated method stub
        return null;
    }

}
