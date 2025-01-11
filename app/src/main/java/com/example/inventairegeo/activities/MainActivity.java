package com.example.inventairegeo.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.inventairegeo.R;
import com.example.inventairegeo.database.DatabaseHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// Activité principale gérant le scan et la navigation
public class MainActivity extends AppCompatActivity {
   // Constantes pour les codes de permission
   private static final int CAMERA_PERMISSION_REQUEST = 100;
   private static final int LOCATION_PERMISSION_REQUEST = 200;
   
   // Variables membres
   private DatabaseHelper dbHelper;
   private FusedLocationProviderClient fusedLocationClient;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       // Initialisation de la base de données et du client de localisation
       dbHelper = new DatabaseHelper(this);
       fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

       // Liaison des éléments UI avec leurs identifiants
       ExtendedFloatingActionButton fabScan = findViewById(R.id.fab_scan);
       Button btnViewList = findViewById(R.id.btn_view_list);

       // Configuration des listeners pour les boutons
       fabScan.setOnClickListener(v -> checkCameraPermission());
       btnViewList.setOnClickListener(v -> openInventoryList());
   }

   // Vérifie la permission de la caméra
   private void checkCameraPermission() {
       if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
               != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this,
                   new String[]{Manifest.permission.CAMERA},
                   CAMERA_PERMISSION_REQUEST);
       } else {
           startBarcodeScanner();
       }
   }

   // Gère les résultats des demandes de permission
   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                          @NonNull int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if (requestCode == CAMERA_PERMISSION_REQUEST) {
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               startBarcodeScanner();
           }
       } else if (requestCode == LOCATION_PERMISSION_REQUEST) {
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Permission de localisation accordée
           }
       }
   }

   // Lance le scanner de code-barres
   private void startBarcodeScanner() {
       try {
           IntentIntegrator integrator = new IntentIntegrator(this);
           integrator.setPrompt("Scannez un code-barres");
           integrator.setBeepEnabled(true);
           integrator.setOrientationLocked(false);
           integrator.initiateScan();
       } catch (Exception e) {
           Toast.makeText(this, "Erreur lors du lancement du scanner", Toast.LENGTH_SHORT).show();
       }
   }

   // Gère le résultat du scan
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       try {
           IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
           if (result != null) {
               if (result.getContents() != null) {
                   String barcode = result.getContents();
                   getCurrentLocation(barcode);
               }
           } else {
               super.onActivityResult(requestCode, resultCode, data);
           }
       } catch (Exception e) {
           Toast.makeText(this, "Erreur lors de la lecture du code-barres", Toast.LENGTH_SHORT).show();
       }
   }

   // Obtient la position actuelle
   private void getCurrentLocation(String barcode) {
       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
               != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this,
                   new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                   LOCATION_PERMISSION_REQUEST);
           return;
       }

       try {
           fusedLocationClient.getLastLocation()
                   .addOnSuccessListener(this, location -> {
                       if (location != null) {
                           openInventoryForm(barcode, location);
                       } else {
                           Toast.makeText(this, "Impossible d'obtenir la position", Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnFailureListener(e -> {
                       Toast.makeText(this, "Erreur de localisation", Toast.LENGTH_SHORT).show();
                   });
       } catch (Exception e) {
           Toast.makeText(this, "Erreur lors de la récupération de la position", Toast.LENGTH_SHORT).show();
       }
   }

   // Ouvre le formulaire d'inventaire avec les données du scan et la position
   private void openInventoryForm(String barcode, Location location) {
       try {
           Intent intent = new Intent(this, InventoryFormActivity.class);
           intent.putExtra("barcode", barcode);
           intent.putExtra("latitude", location.getLatitude());
           intent.putExtra("longitude", location.getLongitude());
           startActivity(intent);
       } catch (Exception e) {
           Toast.makeText(this, "Erreur lors de l'ouverture du formulaire", Toast.LENGTH_SHORT).show();
       }
   }

   // Ouvre la liste d'inventaire
   private void openInventoryList() {
       try {
           Intent intent = new Intent(this, InventoryListActivity.class);
           startActivity(intent);
       } catch (Exception e) {
           Toast.makeText(this, "Erreur lors de l'ouverture de la liste", Toast.LENGTH_SHORT).show();
       }
   }
}
