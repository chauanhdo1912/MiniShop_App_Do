package com.le_do.minishop.data.local.dao

import androidx.room.*
import com.le_do.minishop.data.local.entity.OrderEntity
import com.le_do.minishop.data.local.entity.OrderItemEntity

// DAO für Zugriff auf Bestellungen in der Datenbank
@Dao
interface OrderDao {

    // Neue Bestellung speichern
    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    // Produkte einer Bestellung speichern
    @Insert
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    // Alle Bestellungen eines Benutzers laden
    @Query("SELECT * FROM orders WHERE userEmail = :email ORDER BY createdAt DESC")
    suspend fun getOrdersByUser(email: String): List<OrderEntity>

    // Produkte einer bestimmten Bestellung laden
    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getItemsByOrder(orderId: Long): List<OrderItemEntity>
}