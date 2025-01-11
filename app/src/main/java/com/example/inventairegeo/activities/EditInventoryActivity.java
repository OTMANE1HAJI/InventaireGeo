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

// Cette classe permet de modifier un article existant dans l'inventaire
public class EditInventoryActivity extends AppCompatActivity {
    // Déclaration des éléments de l'interface utilisateur
    private EditText etName, etDescription;
    private Spinner spinnerCategory;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private InventoryItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_edit);

        // Initialisation de l'aide à la base de données
        dbHelper = new DatabaseHelper(this);

        // Liaison des éléments UI avec leurs identifiants
        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnSave = findViewById(R.id.btn_save);

        // Récupération des données passées à l'activité
        int itemId = getIntent().getIntExtra("item_id", -1);
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");

        // Si un ID valide est trouvé, on remplit les champs avec les données existantes
        if (itemId != -1) {
            currentItem = new InventoryItem(itemId, "", name, description, category, 0, 0);
            etName.setText(name);
            etDescription.setText(description);
            setSpinnerCategory(category);
        }

        // Configuration du bouton de sauvegarde
        btnSave.setOnClickListener(v -> saveChanges());
    }

    // Configure le spinner avec la catégorie actuelle de l'article
    private void setSpinnerCategory(String category) {
        // Création de l'adaptateur pour le spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Sélection de la catégorie actuelle dans le spinner
        int position = adapter.getPosition(category);
        if (position >= 0) {
            spinnerCategory.setSelection(position);
        }
    }

    // Sauvegarde les modifications apportées à l'article
    private void saveChanges() {
        if (currentItem != null) {
            // Mise à jour des données de l'article avec les nouvelles valeurs
            currentItem.setName(etName.getText().toString());
            currentItem.setDescription(etDescription.getText().toString());
            currentItem.setCategory(spinnerCategory.getSelectedItem().toString());

            // Tentative de mise à jour dans la base de données
            if (dbHelper.updateItem(currentItem)) {
                Toast.makeText(this, "Modifications enregistrées", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de la sauvegarde", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
