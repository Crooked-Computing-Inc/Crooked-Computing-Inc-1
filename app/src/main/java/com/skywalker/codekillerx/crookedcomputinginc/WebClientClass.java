package com.skywalker.codekillerx.crookedcomputinginc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by harshish on 11/6/17.
 */

public class WebClientClass extends WebViewClient {
    ProgressDialog pd;
    Context context;

    public WebClientClass(Context context) {
        this.context = context;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        pd = new ProgressDialog(context);
        pd.setTitle("Please Wait");
        pd.setMessage("Page is loading..");
        pd.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(pd!=null)
            pd.dismiss();
    }
}
