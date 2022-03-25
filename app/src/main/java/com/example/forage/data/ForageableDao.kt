package com.example.forage.data

import androidx.room.*
import com.example.forage.model.Forageable
import kotlinx.coroutines.flow.Flow

@Dao
interface ForageableDao {
    @Query("SELECT * FROM forageable_table")
    fun getForageables(): Flow<List<Forageable>>

    @Query("SELECT * FROM forageable_table Where id IN (:id)")
    fun getForageable(id: Long): Flow<Forageable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forageable: Forageable)

    @Update
    suspend fun update(forageable: Forageable)

    @Delete
    suspend fun delete(forageable: Forageable)
}