package com.example.chelyabinskgo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chelyabinskgo.data.database.dao.FavoritesDao
import com.example.chelyabinskgo.data.database.entity.FavoriteEventEntity

@Database(
    entities = [FavoriteEventEntity::class],
    version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}