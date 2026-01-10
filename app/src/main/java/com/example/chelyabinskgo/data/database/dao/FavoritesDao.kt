package com.example.chelyabinskgo.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chelyabinskgo.data.database.entity.FavoriteEventEntity
import com.example.chelyabinskgo.data.database.entity.FavoritePlaceEntity

@Dao
interface FavoritesDao {
    @Query("SELECT id FROM favorite_events")
    suspend fun getFavoriteEventIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEventToFavorites(entity: FavoriteEventEntity)

    @Query("DELETE FROM favorite_events WHERE id = :id")
    suspend fun removeEventFromFavorites(id: Int)

    @Query("SELECT id FROM favorite_places")
    suspend fun getFavoritePlaceIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaceToFavorites(entity: FavoritePlaceEntity)

    @Query("DELETE FROM favorite_places WHERE id = :id")
    suspend fun removePlaceFromFavorites(id: Int)
}