package com.skywalker.codekillerx.crookedcomputinginc.MobileSection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.skywalker.codekillerx.crookedcomputinginc.R;
import com.skywalker.codekillerx.crookedcomputinginc.Utils;
import com.skywalker.codekillerx.crookedcomputinginc.WebClientClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AndroidPage extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    private static final String url = "http://crookedcomputing.weebly.com/android.html";
    String desc = "";
    WebView description;
    private static final String TAG = "Androidact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        description = (WebView) findViewById(R.id.androidView);
        description.setClickable(true);
        description.setFocusableInTouchMode(true);
        description.getSettings().setJavaScriptEnabled(false);
        Log.i(TAG,"Starting Async activity");
        new ContentGrab().execute();
    }

    private class ContentGrab extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(AndroidPage.this);
            mProgressDialog.setTitle("Android");
            mProgressDialog.setMessage("Fetching...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.i(TAG,"Background");
                Document document = Jsoup.connect(url).get();
                if(document!=null) {
                    Log.i(TAG, "Got document");
                    Elements para = document.select("div.wsite-multicol");
                    Elements style = document.select("style");
                    String stylesheet = "<link rel=\"stylesheet\" type=\"text/css\" href=\"ccimain.css\" />";
                    if(para!=null){
                        Log.i(TAG,"Got para");
                        //Log.i(TAG,"" + links);
                        Document d = Jsoup.parse(""+ stylesheet + style + "<br />" + "<br />" + para);
                        desc = d.toString();
                        //Log.i(TAG,desc);
                    }
                    else
                        Log.i(TAG,"Para null");
                }else
                    Log.i(TAG,"Doc null");
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressDialog.dismiss();
            if(Utils.isNetworkAvailable(AndroidPage.this)) {
                description.loadDataWithBaseURL("file:///android_asset/",desc,"text/html","UTF-8","");
                WebClientClass webViewClient = new WebClientClass(AndroidPage.this);
                description.setWebViewClient(webViewClient);
            }

        }
    }

}
