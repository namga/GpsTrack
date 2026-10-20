package com.familygps.tracker;

import android.app.Activity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.JavascriptInterface;
import android.os.Build;

public class MainActivity extends Activity {
    private WebView web;
    @Override public void onCreate(Bundle saved) {
        super.onCreate(saved);
        web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.addJavascriptInterface(this, "AndroidGPS");
        setContentView(web);
        web.loadUrl("file:///android_asset/index.html");
    }
    @JavascriptInterface public String getGpsStatus() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return "permission";
        LocationManager lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean enabled=false;
        try { enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER); } catch(Exception e) {}
        return enabled ? "enabled" : "disabled";
    }
    @JavascriptInterface public void requestLocationPermission() {
        runOnUiThread(() -> {
            if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 501);
            }
        });
    }
    @JavascriptInterface public String getLastLocation() {
        try {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) return "";
            LocationManager lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location l=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(l==null) l=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(l==null) return "";
            return l.getLatitude()+","+l.getLongitude()+","+l.getTime();
        } catch(Exception e) { return ""; }
    }
}
