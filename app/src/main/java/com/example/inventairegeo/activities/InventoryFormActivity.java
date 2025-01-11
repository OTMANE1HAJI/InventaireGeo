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

public class InventoryFormActivity extends AppCompatActivity {
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
            dbHelper = new DatabaseHelper(this);

            etName = findViewById(R.id.et_name);
            etDescription = findViewById(R.id.et_description);
            spinnerCategory = findViewById(R.id.spinner_category);
            Button btnSave = findViewById(R.id.btn_save);

            // Configuration du spinner de catégories
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.categories,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);

            // Récupérer les données de l'intent
            barcode = getIntent().getStringExtra("barcode");
            latitude = getIntent().getDoubleExtra("latitude", 0);
            longitude = getIntent().getDoubleExtra("longitude", 0);

            btnSave.setOnClickListener(v -> saveInventoryItem());

        } catch (Exception e) {
            Toast.makeText(this, "Erreur d'initialisation", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveInventoryItem() {
        try {
            // Vérification des champs requis
            if (etName.getText().toString().trim().isEmpty()) {
                etName.setError("Le nom est requis");
                return;
            }

            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();

            // Créer un nouvel item avec la catégorie au lieu de la quantité
            InventoryItem item = new InventoryItem(
                    barcode,
                    name,
                    description,
                    category, // Catégorie à la place de la quantité
                    latitude,
                    longitude
            );

            dbHelper.addInventoryItem(item);
            Toast.makeText(this, "Article enregistré avec succès", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }
}