package com.kuantum.artbook.api

import com.kuantum.artbook.model.ImageResponse
import com.kuantum.artbook.util.Util.API_KEY
import com.kuantum.artbook.util.Util.RESULTS_PER_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("lang") language : String,
        @Query("key") apiKey : String = API_KEY,
        @Query("per_page") resultsPerPage : Int = RESULTS_PER_PAGE
    ) : Response<ImageResponse>

}