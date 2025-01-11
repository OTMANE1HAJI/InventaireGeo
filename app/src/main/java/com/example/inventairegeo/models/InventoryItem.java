package com.example.inventairegeo.models;

public class InventoryItem {
    private int id;
    private String barcode;
    private String name;
    private String description;
    private String category; // Remplace quantity
    private double latitude;
    private double longitude;

    public InventoryItem(String barcode, String name, String description, String category, double latitude, double longitude) {
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public InventoryItem(int id, String barcode, String name, String description, String category, double latitude, double longitude) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters et Setters habituels...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}