package com.le_do.minishop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val totalPrice: Double,
    val createdAt: Long,
    val userEmail: String
)