package com.example.inventairegeo.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventairegeo.R;
import com.example.inventairegeo.database.DatabaseHelper;
import com.example.inventairegeo.models.InventoryItem;

// Cette classe gère l'ajout d'un nouvel article à l'inventaire
public class InventoryFormActivity extends AppCompatActivity {
    // Déclaration des éléments de l'interface utilisateur et des variables
    private EditText etName, etDescription;
    private Spinner spinnerCategory;
    private DatabaseHelper dbHelper;
    private String barcode;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_form);

        try {
            // Initialisation de l'aide à la base de données
            dbHelper = new DatabaseHelper(this);

            // Liaison des éléments UI avec leurs identifiants
            etName = findViewById(R.id.et_name);
            etDescription = findViewById(R.id.et_description);
            spinnerCategory = findViewById(R.id.spinner_category);
            Button btnSave = findViewById(R.id.btn_save);

            // Configuration de l'adaptateur pour le spinner des catégories
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.categories,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);

            // Récupération des données de géolocalisation et du code-barres
            barcode = getIntent().getStringExtra("barcode");
            latitude = getIntent().getDoubleExtra("latitude", 0);
            longitude = getIntent().getDoubleExtra("longitude", 0);

            // Configuration du bouton de sauvegarde
            btnSave.setOnClickListener(v -> saveInventoryItem());

        } catch (Exception e) {
            Toast.makeText(this, "Erreur d'initialisation", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Méthode pour sauvegarder un nouvel article dans l'inventaire
    private void saveInventoryItem() {
        try {
            // Validation du champ nom
            if (etName.getText().toString().trim().isEmpty()) {
                etName.setError("Le nom est requis");
                return;
            }

            // Récupération des valeurs saisies
            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();

            // Création d'un nouvel article
            InventoryItem item = new InventoryItem(
                    barcode,
                    name,
                    description,
                    category,
                    latitude,
                    longitude
            );

            // Sauvegarde dans la base de données
            dbHelper.addInventoryItem(item);
            Toast.makeText(this, "Article enregistré avec succès", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }
}
