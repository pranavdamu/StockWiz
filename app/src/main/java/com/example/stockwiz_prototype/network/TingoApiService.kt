package com.example.stockwiz_prototype.network

import com.example.stockwiz_prototype.data.StockInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TingoApiService {
    @GET("tiingo/daily/{symbol}/prices")
    fun getStockInfo(
        @Path("symbol") symbol: String,
        @Query("token") token: String
    ): Call<List<StockInfo>>  // Assuming API may return a list of daily prices

    @GET("tiingo/daily/{symbol}/prices")
    fun getHistoricalStockInfo(
        @Path("symbol") symbol: String,
        @Query("token") token: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<List<StockInfo>>  // Adjusted for historical data
}