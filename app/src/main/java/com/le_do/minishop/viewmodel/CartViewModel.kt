package com.le_do.minishop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.le_do.minishop.model.Cart
import com.le_do.minishop.model.Product

// ViewModel verwaltet den Warenkorb der App
class CartViewModel : ViewModel() {

    // Liste der Produkte im Warenkorb
    private val _cartItems = MutableLiveData<MutableList<Cart>>(mutableListOf())
    val cartItems: LiveData<MutableList<Cart>> get() = _cartItems

    // Berechnet den Gesamtpreis aller Produkte
    val totalPrice: LiveData<Double> = cartItems.map { items ->
        items.sumOf{it.product.price * it.quantity}
    }

    // Produkt zum Warenkorb hinzufügen
    fun addToCart(product: Product){
        val list = _cartItems.value ?:mutableListOf()
        val existing = list.find {it.product.id == product.id}
        if (existing != null) {
            existing.quantity += 1
        } else {
            list.add(Cart(product,1))
        }
        _cartItems.value = list
    }

    // Produkt aus dem Warenkorb entfernen
    fun removeFromCart(productId:Int){
        val list = _cartItems.value ?: return
        list.removeAll { it.product.id == productId }
        _cartItems.value = list
    }

    // Anzahl eines Produkts erhöhen
    fun increase(productId: Int) {
        val list = _cartItems.value ?: return
        val item = list.find { it.product.id == productId } ?: return
        item.quantity += 1
        _cartItems.value = list
    }

    // Anzahl eines Produkts verringern
    fun decrease(productId: Int) {
        val list = _cartItems.value ?: return
        val item = list.find { it.product.id == productId } ?: return
        item.quantity -= 1
        if (item.quantity <= 0) {
            list.remove(item)
        }
        _cartItems.value = list
    }

    // Warenkorb komplett leeren
    fun clearCart() {
        _cartItems.value = mutableListOf()
    }
}