package com.kuantum.artbook.repo

import androidx.lifecycle.LiveData
import com.kuantum.artbook.api.RetrofitApi
import com.kuantum.artbook.model.ImageResponse
import com.kuantum.artbook.roomdb.Art
import com.kuantum.artbook.roomdb.ArtDao
import com.kuantum.artbook.util.Resource
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val dao : ArtDao,
    private val api : RetrofitApi
) : ArtRepository {
    override suspend fun insertArt(art: Art) {
        dao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        dao.deleteArt(art)
    }

    override fun getArts(): LiveData<List<Art>> {
        return dao.getArts()
    }

    override suspend fun searchImage(search: String, language: String): Resource<ImageResponse> {
        return try {
            val response = api.imageSearch(search, language)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?:  Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }

        } catch (e:Exception) {
            Resource.error("Error", null)
        }
    }
}