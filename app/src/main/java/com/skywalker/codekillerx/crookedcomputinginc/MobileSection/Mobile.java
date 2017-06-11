package com.skywalker.codekillerx.crookedcomputinginc.MobileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.skywalker.codekillerx.crookedcomputinginc.MainActivity;
import com.skywalker.codekillerx.crookedcomputinginc.R;
import com.skywalker.codekillerx.crookedcomputinginc.Utils;
import com.skywalker.codekillerx.crookedcomputinginc.WebClientClass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Mobile extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    private static final String url = "http://crookedcomputing.weebly.com/mobile.html";
    String desc = "";
    WebView description;
    private static final String TAG = "mobact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moblile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mobile.this,AndroidPage.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        description = (WebView) findViewById(R.id.mobileView);
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
            mProgressDialog = new ProgressDialog(Mobile.this);
            mProgressDialog.setTitle("Mobile");
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
                    Elements para = document.select("div.paragraph");
                    Elements titlebar = document.select("h2.wsite-content-title");
                    Elements style = document.select("style");
                    if(para!=null){
                        Log.i(TAG,"Got para");
                        String stylesheet = "<link rel=\"stylesheet\" type=\"text/css\" href=\"ccimain.css\" />";
                        Document d = Jsoup.parse("" + stylesheet + style + titlebar + "<br />" + "<br />" + para);
                        desc = d.toString();
                        Log.i(TAG,desc);
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
            if(Utils.isNetworkAvailable(Mobile.this)) {
                description.loadDataWithBaseURL("file:///android_asset/",desc,"text/html","UTF-8","");
                WebClientClass webViewClient = new WebClientClass(Mobile.this);
                description.setWebViewClient(webViewClient);
            }

        }
    }

}
