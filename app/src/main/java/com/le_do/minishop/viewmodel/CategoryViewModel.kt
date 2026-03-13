package com.le_do.minishop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.le_do.minishop.data.local.ProductRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.le_do.minishop.model.Product
import kotlinx.coroutines.launch

// ViewModel lädt Kategorien und Produkte aus dem Repository
class CategoryViewModel(private val repository: ProductRepository): ViewModel() {

    // Liste der Kategorien
    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    // Liste der Produkte einer Kategorie
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    // Ladezustand für UI
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Kategorien vom Repository laden
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

    // Produkte einer bestimmten Kategorie laden
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