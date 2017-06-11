package com.skywalker.codekillerx.crookedcomputinginc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String URL = "http://crookedcomputing.weebly.com/members.html";
    WebView webView;
    public FloatingActionButton fab_plus,fab_fb,fab_tw,fab_lin,fab_insta;
    public Animation FabOpen,FabClose,FabRClockwise,FabRanticlockwise;
    public static int back_counter = 0;
    boolean isOpen = false;
    String chrome_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRanticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        FabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_fb = (FloatingActionButton) findViewById(R.id.fab_facebook);
        fab_tw = (FloatingActionButton) findViewById(R.id.fab_twitter);
        fab_insta = (FloatingActionButton) findViewById(R.id.fab_instagram);
        fab_lin = (FloatingActionButton) findViewById(R.id.fab_linkedin);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen)
                {
                    fab_fb.startAnimation(FabClose);
                    fab_tw.startAnimation(FabClose);
                    fab_lin.startAnimation(FabClose);
                    fab_insta.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRanticlockwise);
                    fab_fb.setClickable(false);
                    fab_tw.setClickable(false);
                    fab_lin.setClickable(false);
                    fab_insta.setClickable(false);
                    isOpen = false;
                }
                else
                {
                    fab_fb.startAnimation(FabOpen);
                    fab_tw.startAnimation(FabOpen);
                    fab_lin.startAnimation(FabOpen);
                    fab_insta.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockwise);
                    fab_fb.setClickable(true);
                    fab_tw.setClickable(true);
                    fab_lin.setClickable(true);
                    fab_insta.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fab_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrome_url = "https://www.facebook.com/crookedcomputinginc";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(chrome_url));

            }
        });


        fab_tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrome_url = "https://twitter.com/CrookedCompInc";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(chrome_url));
            }
        });


        fab_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrome_url = "https://www.linkedin.com/company-beta/13297829/";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(chrome_url));
            }
        });

        fab_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrome_url = "https://www.instagram.com/crookedcomputinginc/";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(chrome_url));
            }
        });

        webView = (WebView) findViewById(R.id.webView);
        webView.setClickable(true);
        webView.setFocusableInTouchMode(true);
        webView.getSettings().setJavaScriptEnabled(false);
        if(Utils.isNetworkAvailable(this)) {
            webView.loadUrl(URL);
            WebClientClass webViewClient = new WebClientClass(MainActivity.this);
            webView.setWebViewClient(webViewClient);
        }
        else{
            Toast.makeText(this,"Network Service Unavailable",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            URL = "http://crookedcomputing.weebly.com/members.html";
        }else if (id == R.id.nav_work) {
            URL = "http://crookedcomputing.weebly.com/work-with-us.html";
        }

        if(Utils.isNetworkAvailable(MainActivity.this)) {
            webView.loadUrl(URL);
            WebClientClass webViewClient = new WebClientClass(MainActivity.this);
            webView.setWebViewClient(webViewClient);
        }
        else{
            Toast.makeText(this,"Network Service Unavailable",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_vid) {
            URL = "http://crookedcomputing.weebly.com/videos.html";
        } else if (id == R.id.nav_guestcon) {
            URL = "http://crookedcomputing.weebly.com/contributions.html";
        } else if (id == R.id.nav_copyright) {
            URL =  "http://crookedcomputing.weebly.com/copyright-softwares--crooked-computing-inc.html";
        } else if (id == R.id.nav_mobile) {
            //URL = "http://crookedcomputing.weebly.com/mobile.html";
            intent = new Intent(MainActivity.this,Mobile.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_multim) {
            URL = "http://crookedcomputing.weebly.com/multimedia.html";
        }else if (id == R.id.nav_system) {
            URL = "http://crookedcomputing.weebly.com/system.html";
        }else if (id == R.id.nav_util) {
            URL = "http://crookedcomputing.weebly.com/utilities.html";
        }else if (id == R.id.nav_games) {
            URL = "http://crookedcomputing.weebly.com/games.html";
        }else if (id == R.id.nav_dev) {
            URL = "http://crookedcomputing.weebly.com/developers.html";
        } else if (id == R.id.nav_gamerev) {
            URL = "http://crookedcomputing.weebly.com/gaming.html";
        }else if (id == R.id.nav_write) {
            URL = "http://crookedcomputing.weebly.com/write-ups.html";
        }
        else if (id == R.id.nav_hw) {
            URL = "http://crookedcomputing.weebly.com/hardware.html";
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        back_counter = 0;
        if(Utils.isNetworkAvailable(MainActivity.this)) {
            webView.loadUrl(URL);
            WebClientClass webViewClient = new WebClientClass(MainActivity.this);
            webView.setWebViewClient(webViewClient);
        }
        else{
            Toast.makeText(this,"Network Service Unavailable",Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack() && back_counter < 2) {
                        webView.goBack();
                        back_counter++;
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
