package com.le_do.minishop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.le_do.minishop.data.local.ProductRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.le_do.minishop.model.Product
import kotlinx.coroutines.launch
import java.security.KeyStore

class CategoryViewModel(private val repository: ProductRepository): ViewModel() {
    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchCategories(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _categories.value = repository.getCategories()
            }
            finally {
                _isLoading.value = false
            }
        }
    }
    fun fetchProductByCategory(categoryName: String){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _products.value = repository.getProductByCategory(categoryName)
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}