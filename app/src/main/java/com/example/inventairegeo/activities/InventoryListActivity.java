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

public class InventoryListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InventoryAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvEmpty;

    private static final int EDIT_ITEM_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        try {
            dbHelper = new DatabaseHelper(this);
            recyclerView = findViewById(R.id.recycler_view);
            tvEmpty = findViewById(R.id.tv_empty);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            loadInventoryItems();

        } catch (Exception e) {
            Toast.makeText(this, "Erreur d'initialisation de la liste", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadInventoryItems() {
        try {
            List<InventoryItem> items = dbHelper.getAllItems();
            checkEmptyState(items);
        } catch (Exception e) {
            Toast.makeText(this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkEmptyState() {
        List<InventoryItem> items = dbHelper.getAllItems();
        checkEmptyState(items);
    }

    private void checkEmptyState(List<InventoryItem> items) {
        if (items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            adapter = new InventoryAdapter(this, items);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ITEM_REQUEST && resultCode == RESULT_OK) {
            // Recharger la liste après une modification
            loadInventoryItems();
        }
    }
}