package com.le_do.minishop.data.local

import com.le_do.minishop.model.Product
import com.le_do.minishop.network.ApiService
import com.le_do.minishop.viewmodel.CategoryViewModel

class ProductRepository(private val apiService: ApiService) {
    suspend fun getProducts(): List<Product>{
        return apiService.getProducts()
    }
    suspend fun getProductsById(id: Int): Product{
        return apiService.getProductsById(id)
    }
    suspend fun getCategories(): List<String> {
        return apiService.getCategories()
    }
    suspend fun getProductByCategory(categoryName: String): List<Product>{
        return apiService.getProductByCategory(categoryName)
    }

}