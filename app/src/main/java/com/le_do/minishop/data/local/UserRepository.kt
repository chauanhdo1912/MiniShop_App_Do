package com.le_do.minishop.data.local

import com.le_do.minishop.data.local.dao.UserDao
import com.le_do.minishop.data.local.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }
}