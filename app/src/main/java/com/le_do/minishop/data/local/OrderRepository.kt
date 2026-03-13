package com.le_do.minishop.data.local

import com.le_do.minishop.data.local.dao.OrderDao
import com.le_do.minishop.data.local.entity.OrderEntity
import com.le_do.minishop.data.local.entity.OrderItemEntity

// Repository verwaltet Bestellungen in der Datenbank
class OrderRepository(private val orderDao: OrderDao) {

    // Neue Bestellung mit allen Produkten speichern
    suspend fun createOrder(
        order: OrderEntity,
        items: List<OrderItemEntity>
    ) {
        val orderId = orderDao.insertOrder(order)

        val updatedItems = items.map {
            it.copy(orderId = orderId)
        }

        orderDao.insertOrderItems(updatedItems)
    }

    // Bestellungen eines bestimmten Users laden
    suspend fun getOrdersByUser(email: String) =
        orderDao.getOrdersByUser(email)

    // Produkte einer bestimmten Bestellung laden
    suspend fun getItemsByOrder(orderId: Long) =
        orderDao.getItemsByOrder(orderId)
}