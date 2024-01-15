package com.kuantum.artbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.artbook.repo.ArtRepository
import com.kuantum.artbook.roomdb.Art
import com.kuantum.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val repository: ArtRepository
) : ViewModel() {

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(artName: String, artistName: String, year: String, url: String) {
        if (artName.isEmpty() || artistName.isEmpty() || year.isEmpty() || url.isEmpty()) {
            insertArtMsg.value = Resource.error("Enter all info", null)
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e : Exception) {
            insertArtMsg.value = Resource.error("Year should be number", null)
            return
        }

        val art = Art(artName, artistName, yearInt, url)
        insertArt(art)
        insertArtMsg.value = Resource.success(art)
    }

}