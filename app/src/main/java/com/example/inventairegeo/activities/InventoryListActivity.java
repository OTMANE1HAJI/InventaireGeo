package com.example.inventairegeo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inventairegeo.R;
import com.example.inventairegeo.adapters.InventoryAdapter;
import com.example.inventairegeo.database.DatabaseHelper;
import com.example.inventairegeo.models.InventoryItem;
import java.util.List;

// Cette classe affiche la liste des articles de l'inventaire
public class InventoryListActivity extends AppCompatActivity {
    // Déclaration des éléments de l'interface utilisateur
    private RecyclerView recyclerView;
    private InventoryAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvEmpty;

    // Code de requête pour l'édition d'un article
    private static final int EDIT_ITEM_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        try {
            // Initialisation de la base de données et des éléments UI
            dbHelper = new DatabaseHelper(this);
            recyclerView = findViewById(R.id.recycler_view);
            tvEmpty = findViewById(R.id.tv_empty);

            // Configuration du RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Chargement initial des articles
            loadInventoryItems();

        } catch (Exception e) {
            Toast.makeText(this, "Erreur d'initialisation de la liste", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Charge tous les articles depuis la base de données
    private void loadInventoryItems() {
        try {
            List<InventoryItem> items = dbHelper.getAllItems();
            checkEmptyState(items);
        } catch (Exception e) {
            Toast.makeText(this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode publique pour vérifier l'état vide
    public void checkEmptyState() {
        List<InventoryItem> items = dbHelper.getAllItems();
        checkEmptyState(items);
    }

    // Gère l'affichage selon que la liste est vide ou non
    private void checkEmptyState(List<InventoryItem> items) {
        if (items.isEmpty()) {
            // Si la liste est vide, affiche le message
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            // Si la liste contient des éléments, affiche le RecyclerView
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            adapter = new InventoryAdapter(this, items);
            recyclerView.setAdapter(adapter);
        }
    }

    // Gère le retour après édition d'un article
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ITEM_REQUEST && resultCode == RESULT_OK) {
            // Recharge la liste après une modification
            loadInventoryItems();
        }
    }
}
