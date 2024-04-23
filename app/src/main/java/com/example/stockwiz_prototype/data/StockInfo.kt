package com.example.stockwiz_prototype.data

import com.google.gson.annotations.SerializedName

data class StockInfo(
    @SerializedName("adjClose") val adjClose: Double,
    @SerializedName("adjHigh") val adjHigh: Double,
    @SerializedName("adjLow") val adjLow: Double,
    @SerializedName("adjOpen") val adjOpen: Double,
    @SerializedName("adjVolume") val adjVolume: Long,
    @SerializedName("close") val close: Double,
    @SerializedName("high") val high: Double,
    @SerializedName("low") val low: Double,
    @SerializedName("open") val open: Double,
    @SerializedName("volume") val volume: Long,
    @SerializedName("date") val date: String,
)
