package com.napolitano.cordova.plugin.intent;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

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
            String strBarcode = intent.getExtras().getString("barcode_string");
            System.out.println("IntentPlugin::barcode_string::"+strBarcode); 


            String action = intent.getAction();

            String strFinalResult = "";
            String command = intent.getStringExtra("COMMAND");
            String profileName = intent.getStringExtra("PROFILE_NAME");
            String resultInfo = "";

            if (intent.hasExtra("RESULT_LIST")) { // returns for COMPLETE_RESULT
                System.out.println("IntentPlugin::RESULT_LIST"); 
                resultInfo += "ProfileName: " + profileName + "\n";
                ArrayList<Bundle> result_list = (ArrayList)intent.getSerializableExtra("RESULT_LIST");
                for (Bundle bundleResult : result_list) {

                    resultInfo +="\n\n";

                    Set<String> keys = bundleResult.keySet();
                    for (String key : keys) {
                        String val = bundleResult.getString(key);
                        if (val == null) {

                            if (bundleResult.getStringArray(key) != null) {
                                val = "";
                                for (String s : bundleResult.getStringArray(key)) {
                                    val += "" + s + "\n";
                                }
                            }
                        }

                        resultInfo += key + ": " + val + "\n";
                    }
                }
            }
            else if (intent.hasExtra("RESULT_INFO")) { // returns for LAST_RESULT
                System.out.println("IntentPlugin::RESULT_INFO"); 
                String result = intent.getStringExtra("RESULT");
                Bundle bundle = intent.getBundleExtra("RESULT_INFO");
                resultInfo += "Result: " + result + "\n";
                Set<String> keys = bundle.keySet();
                for (String key : keys) {
                    String val = bundle.getString(key);
                    if(val == null) {

                        if(bundle.getStringArray(key) != null) {
                            val="";
                            for (String s : bundle.getStringArray(key)) {
                                val += "" + s + "\n";
                            }
                        }
                    }

                    resultInfo += key + ": " + val + "\n";
                }

            }
            if (command != null) {
                if (command.equalsIgnoreCase("com.symbol.datawedge.api.SET_CONFIG")) {
                    Log.d("TAG", "#IntentApp# \n\nSetConfig status received:\nResultInfo: \n" + resultInfo);
                }
            }

            System.out.println("IntentPlugin::RESULT_INFO::"+resultInfo); 

            callbackContext.success(strBarcode);
        }
    } 
} 