package com.le_do.minishop.data.local

import com.le_do.minishop.data.local.dao.OrderDao
import com.le_do.minishop.data.local.entity.OrderEntity
import com.le_do.minishop.data.local.entity.OrderItemEntity

class OrderRepository(private val orderDao: OrderDao) {

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

    suspend fun getOrdersByUser(email: String) =
        orderDao.getOrdersByUser(email)

    suspend fun getItemsByOrder(orderId: Long) =
        orderDao.getItemsByOrder(orderId)
}