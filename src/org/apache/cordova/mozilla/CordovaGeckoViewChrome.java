package org.apache.cordova.mozilla;

import org.apache.cordova.Config;
import org.mozilla.gecko.GeckoView;
import org.mozilla.gecko.GeckoView.MessageResult;
import org.mozilla.gecko.GeckoViewChrome;
import org.mozilla.gecko.PrefsHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class CordovaGeckoViewChrome extends GeckoViewChrome {
    
    String LOGTAG = "CordovaGeckoViewChrome";
    
    public void onReady(GeckoView view) {
        Log.i(LOGTAG, "Gecko is ready");

        PrefsHelper.setPref("devtools.debugger.remote-enabled", true);

        /* Load URL does nothing, we have to wait unitl things are ready before loading */
        view.addBrowser(Config.getStartUrl());
        //Make sure this is visible regardless of what Cordova does.
        view.setVisibility(View.VISIBLE);
    }
    
    public void onScriptMessage(GeckoView view, Bundle bundle, MessageResult msg) {
        //I'm not sure how this works yet!
    }

}
