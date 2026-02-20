package com.le_do.minishop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.le_do.minishop.data.local.UserRepository
import com.le_do.minishop.data.local.entity.User
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // New user register
    fun register(user: User) {
        viewModelScope.launch {
            try {
                repository.registerUser(user)
                _user.postValue(user)
                _error.postValue(null)
            } catch (e: Exception) {
                _error.postValue("Email đã tồn tại")
            }
        }
    }

    // Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val u = repository.login(email, password)
            if (u != null) {
                _user.postValue(u)
                _error.postValue(null)
            } else {
                _error.postValue("Email or password is incorrect")
            }
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            val u = repository.getUserByEmail(email)
            _user.postValue(u)
        }
    }
}
