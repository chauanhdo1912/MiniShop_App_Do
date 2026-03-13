package com.le_do.minishop.ui
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.le_do.minishop.model.Product
import com.le_do.minishop.network.RetrofitClient
import kotlinx.coroutines.launch
import android.util.Log

// ViewModel lädt die Details eines bestimmten Produkts
class ProductDetailViewModel : ViewModel() {

    // Produktdaten für die Detailseite
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    // Produkt anhand der ID von der API laden
    fun fetchProduct(id: Int){
        viewModelScope.launch {
            try{

                val list = RetrofitClient.apiService.getProducts()

                // Produkt mit passender ID finden
                val result = list.firstOrNull { it.id == id }

                if (result != null) {
                    _product.value = result
                } else {
                    Log.e("ProductDetailVM", "Product not found with id $id")
                }

            } catch (e: Exception){

                // Fehler beim Laden der Produktdaten
                Log.e("ProductDetailVM", "Error fetching product: $e")
            }
        }
    }
}