package com.example.inventairegeo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inventairegeo.R;
import com.example.inventairegeo.activities.EditInventoryActivity;
import com.example.inventairegeo.activities.InventoryListActivity;
import com.example.inventairegeo.database.DatabaseHelper;
import com.example.inventairegeo.models.InventoryItem;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private List<InventoryItem> items;
    private Context context;
    private DatabaseHelper dbHelper;
    private static final int EDIT_ITEM_REQUEST = 100;


    public InventoryAdapter(Context context, List<InventoryItem> items) {
        this.context = context;
        this.items = items;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InventoryItem item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        holder.tvBarcode.setText("Code: " + item.getBarcode());
        // Correction : Mettre à jour l'affichage de la catégorie
        holder.tvCategory.setText("Catégorie: " + item.getCategory());

        // Format de la position
        double latitude = item.getLatitude();
        double longitude = item.getLongitude();
        String locationText = String.format("Position: %.4f, %.4f", latitude, longitude);

        // Générer un lien vers Google Maps
        String mapsLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;

        holder.tvLocation.setText(locationText);
        holder.tvLocation.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mapsLink));
            context.startActivity(intent);
        });

        // Gestion du clic sur le bouton de suppression
        holder.btnDelete.setOnClickListener(v -> {
            int itemId = item.getId();
            boolean deleted = dbHelper.deleteItem(itemId);

            if (deleted) {
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
                Toast.makeText(context, "Article supprimé", Toast.LENGTH_SHORT).show();

                if (items.isEmpty() && context instanceof InventoryListActivity) {
                    ((InventoryListActivity) context).checkEmptyState();
                }
            } else {
                Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
            }
        });

        // Gestion du clic sur le bouton d'édition
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditInventoryActivity.class);
            intent.putExtra("item_id", item.getId());
            intent.putExtra("name", item.getName());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("category", item.getCategory());
            ((Activity) context).startActivityForResult(intent, EDIT_ITEM_REQUEST);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription, tvBarcode, tvCategory, tvLocation;
        MaterialButton btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvBarcode = itemView.findViewById(R.id.tv_barcode);
            tvCategory = itemView.findViewById(R.id.tv_category); // Assurez-vous d'avoir ce TextView dans votre layout
            tvLocation = itemView.findViewById(R.id.tv_location);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }
}