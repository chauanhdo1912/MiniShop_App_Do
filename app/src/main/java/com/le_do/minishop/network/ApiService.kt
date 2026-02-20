package com.le_do.minishop.network

import retrofit2.http.GET
import com.le_do.minishop.model.Product
import retrofit2.http.Path

interface ApiService {
    @GET ("products")
    suspend fun getProducts(): List<Product>
    @GET("product/{id}")
    suspend fun getProductsById(
        @Path("id") id: Int
    ): Product
}