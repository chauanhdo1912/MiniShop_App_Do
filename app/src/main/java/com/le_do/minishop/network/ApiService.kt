package com.le_do.minishop.network

import retrofit2.http.GET
import com.le_do.minishop.model.Product
import retrofit2.http.Path

// API Interface für Produktdaten (Retrofit)
interface ApiService {

    // Alle Produkte von der API laden
    @GET ("products")
    suspend fun getProducts(): List<Product>

    // Einzelnes Produkt über ID laden
    @GET("products/{id}")
    suspend fun getProductsById(
        @Path("id") id: Int
    ): Product

    // Alle Produktkategorien laden
    @GET("products/categories")
    suspend fun getCategories(): List<String>

    // Produkte einer bestimmten Kategorie laden
    @GET("products/category/{category}")
    suspend fun getProductByCategory(@Path("category") categoryName: String): List<Product>

}