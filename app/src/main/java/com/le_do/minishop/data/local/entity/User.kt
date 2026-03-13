package com.le_do.minishop.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey val email: String,
    val name: String,
    val password: String,
    val createdAt: Long,
    val address: String? = null,
    val avatarUri: String? = null
)