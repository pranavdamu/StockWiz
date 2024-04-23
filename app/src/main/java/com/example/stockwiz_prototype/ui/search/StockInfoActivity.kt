package com.example.stockwiz_prototype.ui.search

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stockwiz_prototype.data.StockInfo
import com.example.stockwiz_prototype.databinding.ActivityViewStockBinding
import com.example.stockwiz_prototype.network.RetrofitClient
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class StockInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewStockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewStockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the stock symbol passed as an extra
        val stockSymbol = intent.getStringExtra("stock_symbol")
        binding.companySymbolTv.text = stockSymbol  // Assuming this TextView is for displaying the stock symbol

        // Fetch and display stock info based on the symbol
        fetchStockInfo(stockSymbol)
        fetchHistoricalData(stockSymbol)

    }

    private fun fetchStockInfo(stockSymbol: String?) {
        stockSymbol?.let { symbol ->
            val apiKey = "yourapikey"  // Replace with your actual API key
            RetrofitClient.instance.getStockInfo(symbol, apiKey).enqueue(object :
                Callback<List<StockInfo>> {
                override fun onResponse(call: Call<List<StockInfo>>, response: Response<List<StockInfo>>) {
                    Log.d("API Response", "Full response: ${response.body()}")
                    if (response.isSuccessful && response.body() != null) {
                        val stockInfo = response.body()!!.firstOrNull()  // Assuming you're interested in the most recent entry
                        stockInfo?.let {
                            binding.apply {
                                // Assuming company name needs to be fetched from a different API or hardcoded for now
                                companyNameTv.text = "AAPL"  // Example placeholder TODO
                                stockPriceTv.text = "${it.adjClose}"
                                stockHighTv.text = "${it.high}"
                                stockLowTv.text = "${it.low}"
                                stockPreviousCloseTv.text = "${it.adjClose}"  // Using adjClose for previous close
                                stockPreviousHighTv.text = "${it.adjHigh}"  // Using adjHigh for previous high
                            }
                        }
                    } else {
                        Log.e("StockInfoActivity", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<List<StockInfo>>, t: Throwable) {
                    Log.e("StockInfoActivity", "Network error: ${t.message}", t)
                }
            })
        }
    }
    private fun fetchHistoricalData(symbol: String?) {
        val apiKey = "yourapikey"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)  // Fix date format to be a proper pattern
        val endDate = dateFormat.format(Date())  // Today's date
        val startCalendar = Calendar.getInstance().apply {
            add(Calendar.YEAR, -20)  // Go back 20 years
        }
        val startDate = dateFormat.format(startCalendar.time)

        // Ensure the symbol is not null before making the API call
        symbol?.let {
            RetrofitClient.instance.getHistoricalStockInfo(it, apiKey, startDate, endDate).enqueue(object : Callback<List<StockInfo>> {
                override fun onResponse(call: Call<List<StockInfo>>, response: Response<List<StockInfo>>) {
                    if (response.isSuccessful && response.body() != null) {
                        val stockInfoList = response.body()!!
                        updateChart(stockInfoList)
                    } else {
                        Log.e("StockInfoActivity", "Error fetching historical data: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<List<StockInfo>>, t: Throwable) {
                    Log.e("StockInfoActivity", "Network error", t)
                }
            })
        } ?: run {
            Log.e("StockInfoActivity", "Stock symbol is null")
            // Handle the case when symbol is null
            // E.g., show an error message or use a default symbol
        }
    }

    private fun updateChart(stockInfoList: List<StockInfo>) {
        val entries = stockInfoList.map { stockInfo ->
            // Convert date string to a readable format or a float index if necessary
            val index = stockInfoList.indexOf(stockInfo).toFloat() // Simple index for x-axis
            Entry(index, stockInfo.adjClose.toFloat())
        }

        val dataSet = LineDataSet(entries, "Adjusted Close Price")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.RED
        dataSet.valueTextSize = 12f

        binding.graph.data = LineData(dataSet)
        binding.graph.description.text = "Historical Data"
        binding.graph.invalidate()  // Refresh the chart
    }



}
