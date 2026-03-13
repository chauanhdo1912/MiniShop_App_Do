package com.le_do.minishop.data.local

import com.le_do.minishop.model.Product
import com.le_do.minishop.network.ApiService

// Repository lädt Produktdaten von der API
class ProductRepository(private val apiService: ApiService) {

    // Alle Produkte laden
    suspend fun getProducts(): List<Product>{
        return apiService.getProducts()
    }

    // Produkt anhand der ID laden
    suspend fun getProductsById(id: Int): Product{
        return apiService.getProductsById(id)
    }

    // Kategorien der Produkte laden
    suspend fun getCategories(): List<String> {
        return apiService.getCategories()
    }

    // Produkte einer bestimmten Kategorie laden
    suspend fun getProductByCategory(categoryName: String): List<Product>{
        return apiService.getProductByCategory(categoryName)
    }

}