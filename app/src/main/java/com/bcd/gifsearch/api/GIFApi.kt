package com.bcd.gifsearch.api

import com.bcd.gifsearch.BuildConfig
import com.bcd.gifsearch.data.Categories
import com.bcd.gifsearch.data.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

interface GIFApi {

    companion object {
        const val BASE_URL = "https://tenor.googleapis.com/v2/"
        const val API_KEY = BuildConfig.TENOR_API_KEY
    }

    @GET("categories?key=$API_KEY&client_key=gif_search_app")
    suspend fun getCategories(): Categories

    @GET("search?key=$API_KEY&client_key=gif_search_app&media_filter=gif")
    suspend fun getSearchResults(
        @Query("q") search: String,
        @Query("pos") next: String = "",
        @Query("limit") limit: Int = 8
    ): SearchResults
}
