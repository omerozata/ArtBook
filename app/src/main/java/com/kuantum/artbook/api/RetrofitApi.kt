package com.kuantum.artbook.api

import com.kuantum.artbook.model.ImageResponse
import com.kuantum.artbook.util.Key.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = API_KEY
    ) : Response<ImageResponse>

}