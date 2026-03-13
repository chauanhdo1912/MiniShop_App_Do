package com.le_do.minishop.utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

    fun saveLogin(email: String) {
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putString("email", email)
            .apply()
    }

    fun isLoggedIn(): Boolean =
        prefs.getBoolean("isLoggedIn", false)

    fun getEmail(): String =
        prefs.getString("email", "") ?: ""

    fun logout() {
        prefs.edit().clear().apply()
    }
}