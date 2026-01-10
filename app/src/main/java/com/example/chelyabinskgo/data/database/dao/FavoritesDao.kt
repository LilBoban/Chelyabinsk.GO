package com.example.chelyabinskgo.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chelyabinskgo.data.database.entity.FavoriteEventEntity

@Dao
interface FavoritesDao {
    @Query("SELECT id FROM favorite_events")
    suspend fun getFavoriteIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(entity: FavoriteEventEntity)

    @Query("DELETE FROM favorite_events WHERE id = :id")
    suspend fun removeFromFavorites(id: Int)
}