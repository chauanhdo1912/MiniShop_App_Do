package com.le_do.minishop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey val email: String,  // primary key
    val name: String,
    val password: String,
    val createdAt: Long,

    val address: String? = null,
    val avatarUri: String? = null
)
