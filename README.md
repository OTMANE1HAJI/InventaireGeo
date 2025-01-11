# InventaireGeo

## Introduction

**InventaireGeo** est une application Android permettant de gérer un inventaire de produits en scannant des codes-barres, en enregistrant des informations détaillées et en géolocalisant les produits. Cette application est utile pour les entreprises et particuliers souhaitant suivre leurs articles de manière efficace.

## Fonctionnalités principales

- **Scannage de codes-barres :** Capture de codes-barres pour identifier ou enregistrer des produits.
- **Gestion des produits :** Ajouter, modifier ou supprimer des produits.
- **Géolocalisation :** Enregistrer la position GPS d'un produit.
- **Affichage de l'inventaire :** Visualiser les produits avec des détails comme le nom, la catégorie et la position.
- **Compatibilité hors-ligne :** Utilisation d'une base de données locale pour sauvegarder les données.

## Captures d'écran

### Écran principal
![Écran principal](https://github.com/user-attachments/assets/cf3d720e-5d69-416f-9cb6-69b87e7d4c1c)


### Scannage de code-barres
![Scannage](https://github.com/user-attachments/assets/8c4066a5-5a38-4ed2-aa39-5e6203df78f7)


### Liste de l'inventaire
![Liste d'inventaire](https://github.com/user-attachments/assets/f0d38be2-981a-4ab5-b1eb-dd03fbf36774) ,
                    [](https://github.com/user-attachments/assets/54b7ae32-4581-45a2-b6ca-89db90a73fb5)
)

### Ouverture de localisation
![localisation](https://github.com/user-attachments/assets/b20edfef-d5ef-45ec-895f-abbac86181d4),
               [](https://github.com/user-attachments/assets/7a555121-7351-442c-a45d-8f88a293a9a6)

### Formulaire
![Formulaire](https://github.com/user-attachments/assets/d9d438d5-6118-4819-869e-80a80baaf713)


              

## Prérequis

Avant d'exécuter ce projet, assurez-vous d'avoir les éléments suivants :

- **JDK 8 ou supérieur**
- **Android Studio Bumblebee** ou version ultérieure
- Un appareil ou un émulateur Android fonctionnant sous Android 8.0 ou plus.

## Installation

1. Clonez ce dépôt :
   ```bash
   git clone https://github.com/votre-repository/InventaireGeo.git
   ```

2. Importez le projet dans Android Studio.

3. Synchronisez les dépendances avec Gradle.

4. Compilez et lancez l'application sur un appareil ou un émulateur Android.

## Dépendances

Voici les principales bibliothèques utilisées dans ce projet :

- **Room (SQLite)** : Gestion de la base de données locale.
- **Google Maps API** : Pour la géolocalisation des produits.
- **Camera API** : Utilisée pour scanner les codes-barres.

## Usage

- **Scannage** : Cliquez sur le bouton `Scanner` pour scanner un code-barres.
- **Ajout d’un produit** : Remplissez le formulaire via l’option d’ajout.
- **Localisation** : Consultez la position GPS enregistrée pour chaque produit.

## Structure du projet

Voici la structure de l’application :

- **`java`** : Contient le code source (activités, modèles, adaptateurs, etc.).
- **`res`** : Ressources comme les fichiers XML d'interface, les images et les icônes.
- **`AndroidManifest.xml`** : Configuration principale de l’application (activités, permissions).

## Problèmes connus

- Certains appareils peuvent demander des permissions multiples au lancement. Assurez-vous d'accepter toutes les autorisations nécessaires.
- La précision GPS peut varier en fonction de la connexion réseau.

## Améliorations futures

- Synchronisation avec une base de données cloud.
- Ajout d’une interface utilisateur modernisée.
- Support multilingue pour un public global.

## Auteurs

- **Haji Otmane** 
- **Gomairi Hamza** 
- **El Maimouni Mohamed Amine** 

## License

Ce projet est sous licence [MIT](LICENSE).
