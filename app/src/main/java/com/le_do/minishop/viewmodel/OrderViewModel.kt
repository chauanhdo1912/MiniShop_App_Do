package com.le_do.minishop.viewmodel

import androidx.lifecycle.*
import com.le_do.minishop.data.local.OrderRepository
import com.le_do.minishop.data.local.entity.OrderEntity
import com.le_do.minishop.data.local.entity.OrderItemEntity
import kotlinx.coroutines.launch

// ViewModel verwaltet Bestellungen des Benutzers
class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    // Liste der Bestellungen
    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>> = _orders

    // Neue Bestellung aus dem Warenkorb erstellen
    fun createOrder(
        userEmail: String,
        cartList: List<com.le_do.minishop.model.Cart>
    ) {
        viewModelScope.launch {

            // Gesamtpreis der Bestellung berechnen
            val total = cartList.sumOf {
                it.product.price * it.quantity
            }

            // Order Objekt erstellen
            val order = OrderEntity(
                totalPrice = total,
                createdAt = System.currentTimeMillis(),
                userEmail = userEmail
            )

            // Produkte der Bestellung vorbereiten
            val items = cartList.map {
                OrderItemEntity(
                    orderId = 0, // wird später im Repository gesetzt
                    productId = it.product.id,
                    title = it.product.title,
                    price = it.product.price,
                    quantity = it.quantity
                )
            }

            // Bestellung im Repository speichern
            repository.createOrder(order, items)
        }
    }

    // Bestellungen eines Users laden
    fun loadOrders(email: String) {
        viewModelScope.launch {
            _orders.value = repository.getOrdersByUser(email)
        }
    }
}