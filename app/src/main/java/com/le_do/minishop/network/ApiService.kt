package com.le_do.minishop.network

import retrofit2.http.GET

interface ApiService {
    @GET ("products")
    suspend fun getProduct(): List<Any>
}