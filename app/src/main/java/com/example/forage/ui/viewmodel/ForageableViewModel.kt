package com.example.forage.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.forage.data.ForageDatabase
import com.example.forage.model.Forageable
import com.example.forage.repository.ForageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ForageableViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ForageRepository
    val getForageables: LiveData<List<Forageable>>
    fun getForageable(id: Long): LiveData<Forageable> = repository.getForageable(id).asLiveData()

    init {
        val forageableDao = ForageDatabase.getDatabase(application).forageDao()
        repository = ForageRepository(forageableDao)
        getForageables = repository.getForageables().asLiveData()
    }

    fun addForageable(name: String, address: String, inSeason: Boolean, notes: String) {
        val forageable =
            Forageable(name = name, address = address, inSeason = inSeason, notes = notes)
        viewModelScope.launch(Dispatchers.IO) { repository.insert(forageable) }
    }

    fun updateForageable(
        id: Long,
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable =
            Forageable(id = id, name = name, address = address, inSeason = inSeason, notes = notes)
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(forageable)
        }
    }

    fun deleteForageable(forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(forageable)
        }

    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}