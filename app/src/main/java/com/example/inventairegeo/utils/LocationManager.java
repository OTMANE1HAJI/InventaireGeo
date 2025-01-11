package com.example.inventairegeo.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

// Classe utilitaire pour gérer la localisation
public class LocationManager {
    // Variables membres
    private Context context;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    // Interface pour gérer les callbacks de localisation
    public interface LocationCallback {
        // Appelé quand une position est reçue avec succès
        void onLocationReceived(Location location);
        // Appelé en cas d'erreur de localisation
        void onLocationError();
    }

    // Constructeur
    public LocationManager(Context context, LocationCallback callback) {
        this.context = context;
        this.locationCallback = callback;
        // Initialisation du client de localisation de Google Play Services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    // Méthode pour obtenir la position actuelle
    public void getCurrentLocation() {
        // Vérification de la permission de localisation
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Demande de la dernière position connue
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Si une position est disponible
                            if (location != null) {
                                locationCallback.onLocationReceived(location);
                            } else {
                                // Si aucune position n'est disponible
                                locationCallback.onLocationError();
                            }
                        }
                    });
        } else {
            // Si la permission n'est pas accordée
            locationCallback.onLocationError();
        }
    }
}
