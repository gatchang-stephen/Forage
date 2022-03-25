package com.example.forage.repository

import com.example.forage.data.ForageableDao
import com.example.forage.model.Forageable
import kotlinx.coroutines.flow.Flow

class ForageRepository(private val forageableDao: ForageableDao) {

    fun getForageables(): Flow<List<Forageable>> {
        return forageableDao.getForageables()
    }

    fun getForageable(id: Long): Flow<Forageable> {
        return forageableDao.getForageable(id)
    }

    suspend fun insert(forageable: Forageable) {
        return forageableDao.insert(forageable)
    }

    suspend fun update(forageable: Forageable) {
        return forageableDao.update(forageable)
    }

    suspend fun delete(forageable: Forageable) {
        return forageableDao.delete(forageable)
    }
}