package com.example.inventairegeo.utils;

import android.app.Activity;
import com.google.zxing.integration.android.IntentIntegrator;

// Classe utilitaire pour gérer le scan des codes-barres
public class BarcodeScanner {
    // Instance de l'activité qui utilise le scanner
    private Activity activity;

    // Constructeur qui prend l'activité comme paramètre
    public BarcodeScanner(Activity activity) {
        this.activity = activity;
    }

    // Méthode pour démarrer le scan du code-barres
    public void startScan() {
        // Création d'une instance de IntentIntegrator avec l'activité courante
        IntentIntegrator integrator = new IntentIntegrator(activity);
        // Configuration pour accepter tous les types de codes-barres
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        // Message affiché à l'utilisateur pendant le scan
        integrator.setPrompt("Placez le code-barres dans le cadre");
        // Utilisation de la caméra arrière (id = 0)
        integrator.setCameraId(0);
        // Activation du son lors du scan
        integrator.setBeepEnabled(true);
        // Activation de la sauvegarde de l'image du code-barres
        integrator.setBarcodeImageEnabled(true);
        // Lancement du scan
        integrator.initiateScan();
    }
}
