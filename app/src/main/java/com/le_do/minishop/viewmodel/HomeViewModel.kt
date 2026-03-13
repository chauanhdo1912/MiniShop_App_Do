package com.le_do.minishop.viewmodel

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.le_do.minishop.model.Product
import com.le_do.minishop.network.RetrofitClient
import kotlinx.coroutines.launch

// ViewModel lädt die Produktliste von der API
class HomeViewModel : ViewModel() {

    // LiveData mit allen Produkten
    private val _products = MutableLiveData<List<Product>>()
    val fetchProductLiveData: LiveData<List<Product>> = _products

    // Produkte von der API abrufen
    fun fetchProduct(){
        viewModelScope.launch {
            try {
                val products = RetrofitClient.apiService.getProducts()

                // Produkte an UI senden
                _products.postValue(products)

                Log.d("HomeViewModel","Product size: ${products.size}")
                Log.d("HomeViewModel", products.toString())
            }
            catch (e: Exception){

                // Fehler beim Laden der Produkte
                Log.d("HomeViewModel","Error fetching products",e)
            }
        }
    }
}