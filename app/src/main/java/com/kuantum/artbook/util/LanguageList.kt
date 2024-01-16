package com.kuantum.artbook.util

import android.graphics.drawable.Drawable
import com.kuantum.artbook.R
import com.kuantum.artbook.model.Language

class LanguageList {

    private val defaultLanguage: Language = languageList()[0]

    fun defaultLanguagePosition(): Int {
        return languagePosition(defaultLanguage)
    }

    fun languagePosition(language: Language): Int {
        for (i in languageList().indices) {
            if (language == languageList()[i])
                return i
        }
        return 0
    }

    fun languageList(): List<Language> {
        return listOf(
            Language("Türkçe", "tr", R.drawable.flag),
            Language("English", "en", R.drawable.english),
            Language("Français", "fr", R.drawable.france)
        )
    }
}