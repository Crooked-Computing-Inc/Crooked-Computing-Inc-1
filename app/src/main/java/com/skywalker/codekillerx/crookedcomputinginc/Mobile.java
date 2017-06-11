package com.skywalker.codekillerx.crookedcomputinginc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        description = (WebView) findViewById(R.id.mobileView);
        description.setClickable(true);
        description.setFocusableInTouchMode(true);
        description.getSettings().setJavaScriptEnabled(false);
        /*if(Utils.isNetworkAvailable(this)) {
            description.loadUrl(url);
            WebClientClass webViewClient = new WebClientClass(Mobile.this);
            description.setWebViewClient(webViewClient);
        }*/
        Log.i(TAG,"Starting Async activity");
        new ContentGrab().execute();
        //Toast.makeText(Mobile.this,TAG,Toast.LENGTH_LONG).show();

    }

    private class ContentGrab extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(Mobile.this);
            mProgressDialog.setTitle("Mobile");
            mProgressDialog.setMessage("Loading Please Wait...");
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
                    Elements link = document.select("link[title=wsite-theme-css]");
                    link.attr("href","http://crookedcomputing.weebly.com/files/main_style.css?1496980529");
                    Log.i(TAG,""+link);
                    if(para!=null){
                        Log.i(TAG,"Got para");
                        //Log.i(TAG,"" + links);
                        Document d = Jsoup.parse(""+ link + style + titlebar + "<br />" + "<br />" + para);
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
            description.loadDataWithBaseURL("",desc,"text/html","UTF-8","");
            mProgressDialog.dismiss();
        }
    }

}
