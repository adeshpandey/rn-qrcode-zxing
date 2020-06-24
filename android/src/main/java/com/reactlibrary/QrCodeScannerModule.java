package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import android.widget.Toast;

public class QrCodeScannerModule extends ReactContextBaseJavaModule{

    private final ReactApplicationContext reactContext;

    private Callback mCallback;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      IntentResult result = IntentIntegrator.parseActivityResult(resultCode, intent);
      Toast.makeText(getReactApplicationContext(), result.getContents(), 10).show();
  };
};

    public QrCodeScannerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "QrCodeScanner";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        this.mCallback = callback;
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }

    @ReactMethod
    public void openScanner(boolean isBeepEnable, String prompt,boolean isOrientationLocked, Callback callback){

        this.mCallback = callback;
        Activity a = getCurrentActivity();
        if(a != null){

        new IntentIntegrator(getCurrentActivity())
                    .setPrompt(prompt == null ? "" : prompt)
                    .setOrientationLocked(isOrientationLocked)
                    .setBeepEnabled(isBeepEnable)
                    .initiateScan();
        }
    }

}
