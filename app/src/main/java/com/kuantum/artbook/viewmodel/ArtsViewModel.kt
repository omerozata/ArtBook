package com.kuantum.artbook.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.artbook.model.ImageResponse
import com.kuantum.artbook.repo.ArtRepository
import com.kuantum.artbook.roomdb.Art
import com.kuantum.artbook.util.Resource
import com.kuantum.artbook.util.Util.DEFAULT_SEARCH_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtsViewModel @Inject constructor(
    private val repository: ArtRepository
) : ViewModel() {

    val artList = repository.getArts()

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }
}