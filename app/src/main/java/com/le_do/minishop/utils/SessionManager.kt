package com.le_do.minishop.utils

import android.content.Context

// Verwaltet Login-Session des Benutzers mit SharedPreferences
class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

    // Login-Status und Email speichern
    fun saveLogin(email: String) {
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putString("email", email)
            .apply()
    }

    // Prüfen ob Benutzer eingeloggt ist
    fun isLoggedIn(): Boolean =
        prefs.getBoolean("isLoggedIn", false)

    // Email des eingeloggten Users holen
    fun getEmail(): String =
        prefs.getString("email", "") ?: ""

    // Session löschen (Logout)
    fun logout() {
        prefs.edit().clear().apply()
    }
}