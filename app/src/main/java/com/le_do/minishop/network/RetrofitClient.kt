package com.le_do.minishop.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton für Retrofit Verbindung zur API
object RetrofitClient {

    // Basis URL der FakeStore API
    private const val BASE_URL = "https://fakestoreapi.com"

    // ApiService wird einmal erstellt und wiederverwendet
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl (BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}