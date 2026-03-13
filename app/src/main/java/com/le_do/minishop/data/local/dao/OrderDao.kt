package com.le_do.minishop.data.local.dao

import androidx.room.*
import com.le_do.minishop.data.local.entity.OrderEntity
import com.le_do.minishop.data.local.entity.OrderItemEntity

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("SELECT * FROM orders WHERE userEmail = :email ORDER BY createdAt DESC")
    suspend fun getOrdersByUser(email: String): List<OrderEntity>

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getItemsByOrder(orderId: Long): List<OrderItemEntity>
}