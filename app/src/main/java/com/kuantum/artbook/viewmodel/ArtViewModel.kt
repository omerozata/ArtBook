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
class ArtViewModel @Inject constructor(
    private val repository: ArtRepository,
    private val preferences : SharedPreferences
) : ViewModel() {

    val artList = repository.getArts()


    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    private val selectedLanguage = MutableLiveData<String>()
    val selectedSearchLanguage: LiveData<String>
        get() = selectedLanguage


    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun saveSearchLanguage(language: String) {
        preferences.edit().putString("language", language).apply()
    }

    fun getSearchLanguage() : String {
        return preferences.getString("language", DEFAULT_SEARCH_LANGUAGE).toString()
    }

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedLanguage(language : String) {
        selectedLanguage.value = language
    }

    fun setSelectedImage(url: String) {
        selectedImage.value = url
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(artName: String, artistName: String, year: String) {
        if (artName.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertArtMsg.value = Resource.error("Enter all info", null)
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e : Exception) {
            insertArtMsg.value = Resource.error("Year should be number", null)
            return
        }

        val art = Art(artName, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.value = Resource.success(art)
    }

    fun searchImage(search: String, language: String) {
        if (search.isEmpty())
            return

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(search, language)
            images.value = response
        }
    }

}