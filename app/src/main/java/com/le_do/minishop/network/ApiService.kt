package com.le_do.minishop.network

import retrofit2.http.GET
import com.le_do.minishop.model.Product
import retrofit2.http.Path

interface ApiService {
    @GET ("products")
    suspend fun getProducts(): List<Product>
    @GET("products/{id}")
    suspend fun getProductsById(
        @Path("id") id: Int
    ): Product
    @GET("products/categories")
    suspend fun getCategories(): List<String>
    @GET("products/category/{category}")
    suspend fun getProductByCategory(@Path("category") categoryName: String): List<Product>

}