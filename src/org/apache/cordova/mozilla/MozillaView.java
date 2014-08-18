package org.apache.cordova.mozilla;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.NativeToJsMessageQueue;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.PluginManager;
import org.apache.cordova.PluginResult;
import org.apache.cordova.Whitelist;
import org.json.JSONException;
import org.mozilla.gecko.GeckoView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        pluginManager = new PluginManager(this, this.cordova, null);
        //exposedJsApi = new AndroidExposedJsApi(pluginManager, jsMessageQueue);
        resourceApi = new CordovaResourceApi(this.getContext(), pluginManager);
    }
    

    @Override
    public void setId(int i) {
        super.setId(i);
    }

    @Override
    public void setVisibility(int invisible) {
        super.setVisibility(invisible);
    }


    public void loadUrl(String url) {
        if(currentBrowser == null)
        {
            currentBrowser = this.addBrowser(url);
        }
        else
        {
            currentBrowser.loadUrl(url);
        }
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
        
        loadUrl(url);

    }

    public void stopLoading() {
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
    public void sendJavascript(String statememt) {
        // TODO Auto-generated method stub
        
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
    public void showCustomView(View view, CustomViewCallback callback) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hideCustomView() {
        // TODO Auto-generated method stub
        
    }




    @Override
    public int getVisibility() {
        return super.getVisibility();
    }

    @Override
    public void setOverScrollMode(int overScrollNever) {
        super.setOverScrollMode(overScrollNever);
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
    public View getView() {
        return this;
    }

    @Override
    public String getUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(CordovaInterface cordova, List<PluginEntry> pluginEntries,
            Whitelist whitelist, CordovaPreferences preferences) {
        // TODO Auto-generated method stub
        
    }

    /*
     * WTF is this?
     * @see org.apache.cordova.CordovaWebView#setButtonPlumbedToJs(int, boolean)
     */
    
    
    @Override
    public void setButtonPlumbedToJs(int keyCode, boolean override) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isButtonPlumbedToJs(int keyCode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Whitelist getWhitelist() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CordovaPreferences getPreferences() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onFilePickerResult(Uri uri) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Object postMessage(String id, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

}
