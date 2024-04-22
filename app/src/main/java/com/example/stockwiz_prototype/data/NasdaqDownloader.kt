package com.example.stockwiz_prototype.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

object NasdaqDownloader {

    private const val NASDAQ_SYMBOLS_URL = "https://nasdaqtrader.com/dynamic/SymDir/nasdaqlisted.txt?"

    suspend fun downloadStockList(): List<String>? = withContext(Dispatchers.IO) {
        try {
            Log.d("NasdaqDownloader", "Attempting to download stock list")
            val content = URL(NASDAQ_SYMBOLS_URL).readText()
            Log.d("NasdaqDownloader", "Download successful")
            return@withContext parseStockSymbols(content)
        } catch (e: IOException) {
            Log.e("NasdaqDownloader", "Error downloading stock list: ${e.message}", e)
            return@withContext null
        }
    }

    private fun parseStockSymbols(data: String): List<String> {
        return data.lines().drop(1).mapNotNull { line ->
            line.split('|').firstOrNull()?.takeIf { it.isNotEmpty() }
        }
    }
}
