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

public class EditInventoryActivity extends AppCompatActivity {
    private EditText etName, etDescription;
    private Spinner spinnerCategory;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private InventoryItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_edit);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnSave = findViewById(R.id.btn_save);

        // Récupérer les données de l'intent
        int itemId = getIntent().getIntExtra("item_id", -1);
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");

        if (itemId != -1) {
            currentItem = new InventoryItem(itemId, "", name, description, category, 0, 0);
            etName.setText(name);
            etDescription.setText(description);
            setSpinnerCategory(category);
        }

        btnSave.setOnClickListener(v -> saveChanges());
    }

    private void setSpinnerCategory(String category) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Trouver l'index de la catégorie actuelle
        int position = adapter.getPosition(category);
        if (position >= 0) {
            spinnerCategory.setSelection(position);
        }
    }

    private void saveChanges() {
        if (currentItem != null) {
            currentItem.setName(etName.getText().toString());
            currentItem.setDescription(etDescription.getText().toString());
            currentItem.setCategory(spinnerCategory.getSelectedItem().toString());

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