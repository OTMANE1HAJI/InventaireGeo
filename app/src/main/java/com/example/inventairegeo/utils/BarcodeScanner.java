package com.example.inventairegeo.utils;

import android.app.Activity;
import com.google.zxing.integration.android.IntentIntegrator;

public class BarcodeScanner {
    private Activity activity;

    public BarcodeScanner(Activity activity) {
        this.activity = activity;
    }

    public void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Placez le code-barres dans le cadre");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }
}