package com.le_do.minishop.viewmodel

import androidx.lifecycle.*
import com.le_do.minishop.data.local.entity.User
import com.le_do.minishop.data.local.UserRepository
import kotlinx.coroutines.launch

// ViewModel lädt die Profildaten des Benutzers
class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    // Benutzerdaten für die UI
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    // Benutzer anhand der Email aus der Datenbank laden
    fun loadUser(email: String) {
        viewModelScope.launch {
            repository.getUserByEmail(email)?.let {
                _user.postValue(it)
            }
        }
    }
}