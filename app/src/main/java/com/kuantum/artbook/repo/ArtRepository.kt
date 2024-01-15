package com.kuantum.artbook.repo

import androidx.lifecycle.LiveData
import com.kuantum.artbook.model.ImageResponse
import com.kuantum.artbook.roomdb.Art
import com.kuantum.artbook.util.Resource

interface ArtRepository {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArts(): LiveData<List<Art>>

    suspend fun searchImage(search: String, language: String): Resource<ImageResponse>

}