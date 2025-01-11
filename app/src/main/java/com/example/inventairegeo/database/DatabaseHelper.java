package com.example.inventairegeo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.inventairegeo.models.InventoryItem;

import java.util.ArrayList;
import java.util.List;

// Classe helper pour gérer la base de données SQLite
public class DatabaseHelper extends SQLiteOpenHelper {
    // Constantes de la base de données
    private static final String DATABASE_NAME = "InventoryDB";
    private static final int DATABASE_VERSION = 2; // Version pour la migration

    // Noms des tables et colonnes
    private static final String TABLE_INVENTORY = "inventory";
    private static final String COL_ID = "id";
    private static final String COL_BARCODE = "barcode";
    private static final String COL_NAME = "name";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_CATEGORY = "category";
    private static final String COL_LATITUDE = "latitude";
    private static final String COL_LONGITUDE = "longitude";

    // Constructeur
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Création de la table lors de la première installation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_INVENTORY + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_BARCODE + " TEXT,"
                + COL_NAME + " TEXT,"
                + COL_DESCRIPTION + " TEXT,"
                + COL_CATEGORY + " TEXT,"
                + COL_LATITUDE + " REAL,"
                + COL_LONGITUDE + " REAL"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Mise à jour de la base de données lors d'un changement de version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Sauvegarde des anciennes données
            List<InventoryItem> oldItems = getAllItemsFromOldDB(db);

            // Recréation de la table avec le nouveau schéma
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
            onCreate(db);

            // Restauration des données avec la nouvelle structure
            for (InventoryItem item : oldItems) {
                ContentValues values = new ContentValues();
                values.put(COL_BARCODE, item.getBarcode());
                values.put(COL_NAME, item.getName());
                values.put(COL_DESCRIPTION, item.getDescription());
                values.put(COL_CATEGORY, "Autre"); 
                values.put(COL_LATITUDE, item.getLatitude());
                values.put(COL_LONGITUDE, item.getLongitude());
                db.insert(TABLE_INVENTORY, null, values);
            }
        }
    }

    // Récupère l'index d'une colonne de manière sécurisée
    private int getColumnIndexSafely(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            Log.w("DatabaseHelper", "Column '" + columnName + "' not found in the database.");
        }
        return index;
    }

    // Récupère tous les éléments de l'ancienne version de la DB
    private List<InventoryItem> getAllItemsFromOldDB(SQLiteDatabase db) {
        List<InventoryItem> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY;

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Récupération sécurisée des indices des colonnes
                    int barcodeIndex = getColumnIndexSafely(cursor, COL_BARCODE);
                    int nameIndex = getColumnIndexSafely(cursor, COL_NAME);
                    int descriptionIndex = getColumnIndexSafely(cursor, COL_DESCRIPTION);
                    int latitudeIndex = getColumnIndexSafely(cursor, COL_LATITUDE);
                    int longitudeIndex = getColumnIndexSafely(cursor, COL_LONGITUDE);

                    // Récupération des valeurs avec gestion des erreurs
                    String barcode = barcodeIndex != -1 ? cursor.getString(barcodeIndex) : null;
                    String name = nameIndex != -1 ? cursor.getString(nameIndex) : null;
                    String description = descriptionIndex != -1 ? cursor.getString(descriptionIndex) : null;
                    double latitude = latitudeIndex != -1 ? cursor.getDouble(latitudeIndex) : 0.0;
                    double longitude = longitudeIndex != -1 ? cursor.getDouble(longitudeIndex) : 0.0;

                    items.add(new InventoryItem(barcode, name, description, "Autre", latitude, longitude));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while getting old items", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return items;
    }

    // Ajoute un nouvel article dans la base de données
    public void addInventoryItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Préparation des valeurs à insérer
        values.put(COL_BARCODE, item.getBarcode());
        values.put(COL_NAME, item.getName());
        values.put(COL_DESCRIPTION, item.getDescription());
        values.put(COL_CATEGORY, item.getCategory());
        values.put(COL_LATITUDE, item.getLatitude());
        values.put(COL_LONGITUDE, item.getLongitude());

        db.insert(TABLE_INVENTORY, null, values);
        db.close();
    }

    // Récupère tous les articles de la base de données
    public List<InventoryItem> getAllItems() {
        List<InventoryItem> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Récupération sécurisée des indices et valeurs
                    int idIndex = getColumnIndexSafely(cursor, COL_ID);
                    int barcodeIndex = getColumnIndexSafely(cursor, COL_BARCODE);
                    int nameIndex = getColumnIndexSafely(cursor, COL_NAME);
                    int descriptionIndex = getColumnIndexSafely(cursor, COL_DESCRIPTION);
                    int categoryIndex = getColumnIndexSafely(cursor, COL_CATEGORY);
                    int latitudeIndex = getColumnIndexSafely(cursor, COL_LATITUDE);
                    int longitudeIndex = getColumnIndexSafely(cursor, COL_LONGITUDE);

                    // Création de l'objet InventoryItem avec les valeurs récupérées
                    String barcode = barcodeIndex != -1 ? cursor.getString(barcodeIndex) : null;
                    String name = nameIndex != -1 ? cursor.getString(nameIndex) : null;
                    String description = descriptionIndex != -1 ? cursor.getString(descriptionIndex) : null;
                    String category = categoryIndex != -1 ? cursor.getString(categoryIndex) : "Autre";
                    double latitude = latitudeIndex != -1 ? cursor.getDouble(latitudeIndex) : 0.0;
                    double longitude = longitudeIndex != -1 ? cursor.getDouble(longitudeIndex) : 0.0;

                    InventoryItem item = new InventoryItem(barcode, name, description, category, latitude, longitude);
                    if (idIndex != -1) {
                        item.setId(cursor.getInt(idIndex));
                    }
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while getting items", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return items;
    }

    // Met à jour un article existant
    public boolean updateItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Mise à jour des valeurs modifiables
        values.put(COL_NAME, item.getName());
        values.put(COL_DESCRIPTION, item.getDescription());
        values.put(COL_CATEGORY, item.getCategory());

        // Exécution de la mise à jour
        int result = db.update(TABLE_INVENTORY,
                values,
                "id = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
        return result > 0;
    }

    // Supprime un article de la base de données
    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_INVENTORY, COL_ID + " = ?", new String[]{String.valueOf(itemId)});
        db.close();
        return result > 0;
    }
}
