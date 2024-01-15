package com.kuantum.artbook.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.artbook.model.ImageResponse
import com.kuantum.artbook.repo.ArtRepository
import com.kuantum.artbook.util.Resource
import com.kuantum.artbook.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageApiViewModel @Inject constructor(
    private val preferences : SharedPreferences,
    private val repository: ArtRepository
) : ViewModel() {

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images


    private val selectedLanguage = MutableLiveData<String>()
    val selectedSearchLanguage: LiveData<String>
        get() = selectedLanguage

    fun saveSearchLanguage(language: String) {
        preferences.edit().putString("language", language).apply()
    }

    fun getSearchLanguage() : String {
        return preferences.getString("language", Util.DEFAULT_SEARCH_LANGUAGE).toString()
    }

    fun setSelectedLanguage(language : String) {
        selectedLanguage.value = language
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