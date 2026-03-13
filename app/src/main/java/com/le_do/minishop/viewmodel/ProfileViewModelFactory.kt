package com.le_do.minishop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.le_do.minishop.data.local.UserRepository

// Factory erstellt ProfileViewModel mit Repository
class ProfileViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {

    // Erstellt eine Instanz von ProfileViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }

        // Fehler wenn falsches ViewModel angefordert wird
        throw IllegalArgumentException("Unknown ViewModel")
    }
}