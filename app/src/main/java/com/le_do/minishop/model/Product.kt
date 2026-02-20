package com.le_do.minishop.model

data class Product (
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String? = null,
    val image: String? = null
)
