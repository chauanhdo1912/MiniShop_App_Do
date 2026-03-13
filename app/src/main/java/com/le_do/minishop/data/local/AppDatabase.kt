package com.le_do.minishop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.le_do.minishop.data.local.dao.OrderDao
import com.le_do.minishop.data.local.dao.UserDao
import com.le_do.minishop.data.local.entity.User
import com.le_do.minishop.data.local.entity.OrderEntity
import com.le_do.minishop.data.local.entity.OrderItemEntity

// Room Datenbank der App
@Database(
    entities = [User::class,
        OrderEntity::class,
        OrderItemEntity::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

    // Zugriff auf User Tabelle
    abstract fun userDao(): UserDao

    // Zugriff auf Order Tabelle
    abstract fun OrderDao(): OrderDao

    companion object {

        // Singleton Instanz der Datenbank
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Datenbank erstellen oder vorhandene Instanz zurückgeben
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "minishop_db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}