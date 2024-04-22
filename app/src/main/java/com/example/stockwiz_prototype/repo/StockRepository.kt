package com.crazzyghost.stockmonitor.data

import java.io.StringReader
import java.util.ArrayList
import com.opencsv.CSVReader
import com.example.stockwiz_prototype.data.NasdaqDownloader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

object StockRepository {

    fun getStockList(scope: CoroutineScope, callback: (Result<List<String>>) -> Unit) {
        scope.launch {
            val result = try {
                Result.success(NasdaqDownloader.downloadStockList() ?: throw IOException("Failed to download data"))
            } catch (e: Exception) {
                Result.failure<List<String>>(e)
            }
            callback(result)
        }

}}
