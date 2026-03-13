package com.le_do.minishop.viewmodel

import androidx.lifecycle.*
import com.le_do.minishop.data.local.OrderRepository
import com.le_do.minishop.data.local.entity.OrderEntity
import com.le_do.minishop.data.local.entity.OrderItemEntity
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>> = _orders

    fun createOrder(
        userEmail: String,
        cartList: List<com.le_do.minishop.model.Cart>
    ) {
        viewModelScope.launch {

            val total = cartList.sumOf {
                it.product.price * it.quantity
            }

            val order = OrderEntity(
                totalPrice = total,
                createdAt = System.currentTimeMillis(),
                userEmail = userEmail
            )

            val items = cartList.map {
                OrderItemEntity(
                    orderId = 0, // sẽ được update trong repository
                    productId = it.product.id,
                    title = it.product.title,
                    price = it.product.price,
                    quantity = it.quantity
                )
            }

            repository.createOrder(order, items)
        }
    }

    fun loadOrders(email: String) {
        viewModelScope.launch {
            _orders.value = repository.getOrdersByUser(email)
        }
    }
}