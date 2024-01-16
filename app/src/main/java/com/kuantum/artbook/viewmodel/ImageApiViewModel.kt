package com.kuantum.artbook.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.artbook.model.ImageResponse
import com.kuantum.artbook.model.Language
import com.kuantum.artbook.repo.ArtRepository
import com.kuantum.artbook.util.LanguageList
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

    private val selectedLanguage = MutableLiveData<Int>()
    val selectedSearchLanguage: LiveData<Int>
        get() = selectedLanguage

    fun saveSearchLanguage(languagePosition: Int) {
        preferences.edit().putInt("language", languagePosition).apply()
    }

    fun getSearchLanguagePosition() : Int {
        return preferences.getInt("language", LanguageList().defaultLanguagePosition())
    }

    fun setSelectedLanguage(language : Int) {
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