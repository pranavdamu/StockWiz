package com.example.stockwiz_prototype.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.tiingo.com/"

    // Lazy initialization of the Retrofit service
    val instance: TingoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TingoApiService::class.java)
    }
}
