package com.example.inventairegeo.models;

public class InventoryItem {
    // Déclaration des attributs de l'article d'inventaire
    private int id;                // Identifiant unique pour chaque article
    private String barcode;        // Code-barres unique de l'article
    private String name;           // Nom de l'article
    private String description;    // Description détaillée de l'article
    private String category;       // Catégorie de l'article (remplace la quantité)
    private double latitude;       // Latitude de l'emplacement de l'article
    private double longitude;      // Longitude de l'emplacement de l'article

    // Constructeur pour initialiser un article sans id (utilisé lors de la création d'un nouvel article)
    public InventoryItem(String barcode, String name, String description, String category, double latitude, double longitude) {
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Constructeur pour initialiser un article avec id (utilisé lors de la récupération d'un article existant depuis la base de données)
    public InventoryItem(int id, String barcode, String name, String description, String category, double latitude, double longitude) {
        this.id = id;                
        this.barcode = barcode;      
        this.name = name;           
        this.description = description; 
        this.category = category;   
        this.latitude = latitude;    
        this.longitude = longitude;  
    }

    // Getters et Setters habituels pour chaque attribut, permettant d'accéder et de modifier les valeurs des propriétés
    
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
