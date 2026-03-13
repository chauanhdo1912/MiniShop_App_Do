package com.le_do.minishop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.le_do.minishop.data.local.ProductRepository

// Factory zum Erstellen von CategoryViewModel mit Repository
class CategoryViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {

    // Erstellt eine Instanz von CategoryViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)){
            return CategoryViewModel(repository) as T
        }

        // Fehler wenn falsches ViewModel angefordert wird
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}