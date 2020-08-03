package com.napolitano.cordova.plugin.intent;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.MediaStore;
import android.database.Cursor;
import android.content.ClipData;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.net.Uri;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.webkit.MimeTypeMap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

public class BarcodeReceiverProindar extends BroadcastReceiver {

    private CallbackContext callbackContext;

    public BarcodeReceiverProindar (CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    public void onReceive(Context ctx, Intent intent) {
        System.out.println("IntentPlugin::onReceive");
        if (intent.getAction().equals("com.symbol.datawedge.api.RESULT_ACTION")) {
            
            System.out.println("IntentPlugin::com.symbol.datawedge.api.RESULT_ACTION");
            String strBarcode = intent.getExtras().getString("com.symbol.datawedge.data_string");
            System.out.println("IntentPlugin::barcode_string::"+strBarcode); 

            /*
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    System.out.println("IntentArrigoni::"+key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            */
            callbackContext.success(strBarcode);
        }
    } 
} 