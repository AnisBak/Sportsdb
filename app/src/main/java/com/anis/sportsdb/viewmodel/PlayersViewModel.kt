package com.anis.sportsdb.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anis.sportsdb.model.Player
import com.anis.sportsdb.repositories.SportdbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryViewModel"

class PlayersViewModel : ViewModel() {
    private val sportdbRepository = SportdbRepository()

    private val _galleryItems: MutableStateFlow<List<Player>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<Player>>
        get() = _galleryItems.asStateFlow()


    fun setQuery(query: String) {
        viewModelScope.launch {
            try {
                val items = fetchGalleryItems(query)
                _galleryItems.value = items?: emptyList()
                Log.d(TAG, "Items received: $items")
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
        }
    }
        private suspend fun fetchGalleryItems(query: String): List<Player>? {
            return sportdbRepository.searchPhotos(query)
        }
    }
