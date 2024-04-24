package com.example.stockwiz_prototype.network

import com.example.stockwiz_prototype.model.NewsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun getNewsArticles(
        @Query("q") query: String,
        @Query("language") language: String = "en",
        @Query("sortBy") sortBy: String = "relevancy",
        @Query("apiKey") apiKey: String
    ): Call<NewsItem.NewsResponse>
}
