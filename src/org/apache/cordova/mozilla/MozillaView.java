package org.apache.cordova.mozilla;

import java.util.HashMap;
import java.util.Locale;

import org.apache.cordova.AndroidExposedJsApi;
import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.LOG;
import org.apache.cordova.NativeToJsMessageQueue;
import org.apache.cordova.PluginManager;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.mozilla.gecko.GeckoView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.LinearLayout.LayoutParams;

public class MozillaView extends GeckoView implements CordovaWebView{

    private static final String TAG = "MozillaView";

    //The CordovaInterface, we need to have access to this!
    private CordovaInterface cordova;
    private String url;
    
    public PluginManager pluginManager;
    
    //Geckoview's current browser object! 
    private Browser currentBrowser;

    private int loadUrlTimeout;

    private CordovaResourceApi resourceApi;

    public MozillaView(Context context) {
        super(context);
        if (CordovaInterface.class.isInstance(context))
        {
            this.cordova = (CordovaInterface) context;
        }
        this.loadConfiguration();
        this.setup();

    }
    
    /**
     * Check configuration parameters from Config.
     * Approved list of URLs that can be loaded into Cordova
     *      <access origin="http://server regexp" subdomains="true" />
     * Log level: ERROR, WARN, INFO, DEBUG, VERBOSE (default=ERROR)
     *      <log level="DEBUG" />
     */
    private void loadConfiguration() {
 
        if ("true".equals(this.getProperty("Fullscreen", "false"))) {
            this.cordova.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            this.cordova.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    /**
     * Get string property for activity.
     *
     * @param name
     * @param defaultValue
     * @return the String value for the named property
     */
    public String getProperty(String name, String defaultValue) {
        Bundle bundle = this.cordova.getActivity().getIntent().getExtras();
        if (bundle == null) {
            return defaultValue;
        }
        name = name.toLowerCase(Locale.getDefault());
        Object p = bundle.get(name);
        if (p == null) {
            return defaultValue;
        }
        return p.toString();
    }

    private void setup()
    {
        pluginManager = new PluginManager(this, this.cordova);
        //exposedJsApi = new AndroidExposedJsApi(pluginManager, jsMessageQueue);
        resourceApi = new CordovaResourceApi(this.getContext(), pluginManager);
    }
    

    @Override
    public void setWebViewClient(CordovaWebViewClient webViewClient) {
        // We don't need the webViewClient
    }

    @Override
    public void setWebChromeClient(CordovaChromeClient webChromeClient) {
         //We don't need the webChromeClient
    }

    @Override
    public void setId(int i) {
        super.setId(i);
    }

    @Override
    public void setVisibility(int invisible) {
        super.setVisibility(invisible);
    }

    @Override
    public void loadUrl(String url, int splashscreenTime) {
       
    }

    @Override
    public void loadUrl(String url) {
        if (url.equals("about:blank") || url.startsWith("javascript:")) {
            this.loadUrlNow(url);
        }
        else {

            String initUrl = this.getProperty("url", null);

            // If first page of app, then set URL to load to be the one passed in
            if (initUrl == null) {
                this.loadUrlIntoView(url);
            }
            // Otherwise use the URL specified in the activity's extras bundle
            else {
                this.loadUrlIntoView(initUrl);
            }
        }    
    }

    private void loadUrlIntoView(String initUrl) {
        loadUrlIntoView(initUrl, true);
    }
    
    /**
     * Load the url into the webview.
     *
     * @param url
     */
    public void loadUrlIntoView(final String url, boolean recreatePlugins) {
        LOG.d(TAG, ">>> loadUrl(" + url + ")");

        if (recreatePlugins) {
            this.url = url;
            this.pluginManager.init();
        }
        
        loadUrlNow(url);

    }


    protected void stopLoading() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean canGoBack() {
        if(currentBrowser != null)
        {
            return currentBrowser.canGoBack();
        }
        return false;
    }

    @Override
    public void clearCache(boolean b) {
        //TODO: Implement this
    }

    @Override
    public void clearHistory() {
        //
    }

    @Override
    public boolean backHistory() {
        boolean returnValue = currentBrowser.canGoBack();
        if(returnValue)
        {
            currentBrowser.goBack();
        }
        return returnValue;
    }

    @Override
    public void handlePause(boolean keepRunning) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleResume(boolean keepRunning,
            boolean activityResultKeepRunning) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleDestroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postMessage(String id, Object data) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addJavascript(String statement) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendJavascript(String statememt) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CordovaChromeClient getWebChromeClient() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CordovaPlugin getPlugin(String initCallbackClass) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void showWebPage(String errorUrl, boolean b, boolean c,
            HashMap<String, Object> params) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public View getFocusedChild() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCustomViewShowing() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String exec(String service, String action, String callbackId,
            String message) throws JSONException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNativeToJsBridgeMode(int parseInt) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String retrieveJsMessages(boolean equals) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void showCustomView(View view, CustomViewCallback callback) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hideCustomView() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public boolean onOverrideUrlLoading(String url) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resetJsMessageQueue() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onReset() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getVisibility() {
        return super.getVisibility();
    }

    @Override
    public void incUrlTimeout() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setOverScrollMode(int overScrollNever) {
        super.setOverScrollMode(overScrollNever);
    }

    @Override
    public void loadUrlNow(String string) {
        if(currentBrowser == null)
        {
            currentBrowser = this.addBrowser(string);
        }
        else
        {
            currentBrowser.loadUrl(string);
        }
    }

    @Override
    public void setNetworkAvailable(boolean online) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CordovaResourceApi getResourceApi() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void bindButton(boolean override) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void bindButton(String button, boolean override) {
        // TODO Auto-generated method stub
        
    }
    
    
    @Override
    public boolean isBackButtonBound() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void sendPluginResult(PluginResult cr, String callbackId) {
        // TODO Auto-generated method stub
        
    }

    /*
     * (non-Javadoc)
     * This is Mozilla's method
     * 
     * @see org.apache.cordova.CordovaWebView#getPluginManager()
     */
    
    @Override
    public PluginManager getPluginManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLayoutParams(
            android.widget.LinearLayout.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
    }

    @Override
    public void setLayoutParams(LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
    }

}
