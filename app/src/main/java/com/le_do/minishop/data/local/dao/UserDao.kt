package com.le_do.minishop.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import com.le_do.minishop.data.local.entity.User
import androidx.room.Update

// DAO für Zugriff auf Benutzer in der Datenbank
@Dao
interface UserDao {

    // Neuen Benutzer registrieren
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: User)

    // Benutzer anhand der Email laden
    @Query("SELECT * FROM UserTable WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // Login prüfen (Email + Passwort)
    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    suspend fun login(email: String,password: String): User?

    // Benutzerdaten aktualisieren
    @Update
    suspend fun updateUser(user: User)

}