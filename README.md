# MiniShop Android App

## Student
Name: Le Chau Anh Do
Kurs: App Entwicklung mit Android WiSe-2025
Dozent: Holger Zimmermann

---

# Project Description

MiniShop ist eine einfache Android-Shopping-App, die mit Kotlin entwickelt wurde und der MVVM-Architektur folgt.  
Die Anwendung ermöglicht es Benutzern, Produkte aus einer Online-API anzusehen, Produktdetails zu öffnen, Produkte zum Warenkorb hinzuzufügen, Bestellungen zu erstellen und ihr Profil zu verwalten.

Dieses Projekt zeigt die Verwendung verschiedener Android-Technologien, zum Beispiel:

- Kotlin
- MVVM Architektur
- Retrofit (REST API)
- Room Database
- LiveData und ViewModel
- Navigation Component
- SharedPreferences (Session Management)

---

# Application Features

Die Anwendung bietet folgende Funktionen:

### User Authentication
- Registrierung eines neuen Benutzers mit Email und Passwort
- Login mit vorhandenen Zugangsdaten
- Verwaltung der Login-Session über SharedPreferences

### Product Browsing
- Laden der Produktdaten über die FakeStore API
- Anzeige einer Liste von Produkten auf der Home-Seite
- Öffnen einer Detailseite für jedes Produkt

### Category Filtering
- Anzeige der verfügbaren Produktkategorien
- Filtern der Produkte nach Kategorie

### Shopping Cart
- Produkte zum Warenkorb hinzufügen
- Anzahl der Produkte erhöhen oder verringern
- Produkte aus dem Warenkorb entfernen
- Automatische Berechnung des Gesamtpreises

### Order Management
- Erstellung einer Bestellung aus dem Warenkorb
- Speicherung der Bestelldaten in der Room Database
- Anzeige der Bestellhistorie im Bereich "My Orders"

### User Profile
- Anzeige der Benutzerinformationen
- Bearbeiten der Adresse und des Avatars
- Aktivieren oder Deaktivieren des Dark Mode
- Logout Funktion

---

# How to Use the App

### 1. Start the Application
Beim Start der App muss sich der Benutzer zunächst anmelden oder registrieren.

### 2. Register a New Account
- Öffnen der Register-Seite
- Eingabe von Name, Email und Passwort
- Absenden des Registrierungsformulars

### 3. Login
- Eingabe von Email und Passwort
- Nach erfolgreichem Login wird der Benutzer zur Home-Seite weitergeleitet

### 4. Browse Products
- Produkte werden über die API geladen
- Durch Klick auf ein Produkt öffnet sich die Produktdetailseite

### 5. Add Product to Cart
- Auf der Produktdetailseite kann der Benutzer auf **Add to Cart** klicken
- Das Produkt wird zum Warenkorb hinzugefügt

### 6. Manage Cart
- Anzeige der Produkte im Warenkorb
- Erhöhen oder Verringern der Menge
- Entfernen von Produkten
- Anzeige des Gesamtpreises

### 7. Place an Order
- Klick auf **Checkout**
- Die Bestellung wird in der Room Database gespeichert
- Nach erfolgreicher Bestellung wird der Warenkorb geleert

### 8. View Orders
- Navigation zum Bereich **My Orders**
- Anzeige der bisherigen Bestellungen

### 9. Edit Profile
- Öffnen des Bereichs **Account**
- Bearbeiten der Adresse oder des Profilbildes
- Speichern der Änderungen

### 10. Logout
- Klick auf den Logout-Button im Profilbereich

---

# Project Structure

Die Anwendung folgt der MVVM Architektur:

UI (Fragments)
↓
ViewModels
↓
Repositories
↓
Room Database / API

Die wichtigsten Komponenten sind:

- **Fragments** für die Benutzeroberfläche
- **ViewModels** für die Logik der Anwendung
- **Repositories** für Datenzugriff
- **Room Database** für lokale Datenspeicherung
- **Retrofit API** für das Laden der Produktdaten

---

# Included Submission Files

Alle Dateien können direkt in diesem GitHub Repository gefunden werden:

- Source Code der Android-Anwendung
- Eine lauffähige **APK Datei**
- Das aktuelle **Ablaufdiagramm der App**
- Diese **README Datei mit Projektinformationen**

---

# Quellen:

## API Used

Die Anwendung verwendet folgende öffentliche API:

https://fakestoreapi.com

## Icon von dem App

https://www.flaticon.com/free-icon/online-shop_7529510
---
