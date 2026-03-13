package com.le_do.minishop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.le_do.minishop.data.local.UserRepository

// Factory erstellt UserViewModel mit Repository
class UserViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {

    // Erstellt eine Instanz von UserViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }

        // Fehler wenn falsches ViewModel angefordert wird
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}