package com.le_do.minishop.data.local

import com.le_do.minishop.data.local.dao.UserDao
import com.le_do.minishop.data.local.entity.User

// Repository verwaltet Benutzer in der Datenbank
class UserRepository(private val userDao: UserDao) {

    // Neuen Benutzer registrieren
    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    // Benutzer anhand der Email suchen
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    // Login überprüfen
    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    // Benutzerprofil aktualisieren
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}