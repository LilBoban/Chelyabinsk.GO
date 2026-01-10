package com.example.chelyabinskgo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chelyabinskgo.data.database.dao.FavoritesDao
import com.example.chelyabinskgo.data.database.entity.FavoriteEventEntity
import com.example.chelyabinskgo.data.database.entity.FavoritePlaceEntity

@Database(
    entities = [FavoriteEventEntity::class, FavoritePlaceEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}