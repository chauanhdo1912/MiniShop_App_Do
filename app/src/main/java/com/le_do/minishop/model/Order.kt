package com.le_do.minishop.model



data class Order (
    val id: Long,
    val item: List<Cart>,
    val totalPrice: Double,
    val createAt: Long,
    val userEmail: String


)