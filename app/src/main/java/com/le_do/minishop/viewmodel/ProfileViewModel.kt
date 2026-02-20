package com.le_do.minishop.viewmodel

import androidx.lifecycle.*
import com.le_do.minishop.data.local.entity.User
import com.le_do.minishop.data.local.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser(email: String) {
        viewModelScope.launch {
            repository.getUserByEmail(email)?.let {
                _user.postValue(it)
            }
        }
    }
}
