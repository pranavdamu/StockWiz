package com.example.stockwiz_prototype.ui.newsinfo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockwiz_prototype.R
import com.example.stockwiz_prototype.adapter.NewsAdapter
import com.example.stockwiz_prototype.databinding.ActivityStockNewsInfoBinding
import com.example.stockwiz_prototype.model.NewsItem
import com.example.stockwiz_prototype.network.NewsApiService
import com.example.stockwiz_prototype.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StockNewsInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStockNewsInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockNewsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stockSymbol = intent.getStringExtra("stock_symbol") ?: return
        fetchNews(stockSymbol)
    }

    private fun fetchNews(stockSymbol: String) {
        val apiKey = "a7ba9917efb44c2fa56b207540168f9b"
        RetrofitClient.instance2.getNewsArticles(stockSymbol, apiKey = apiKey).enqueue(object : Callback<NewsItem.NewsResponse> {
            override fun onResponse(call: Call<NewsItem.NewsResponse>, response: Response<NewsItem.NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val newsArticles = response.body()!!.articles
                    if (newsArticles.isNotEmpty()) {
                        setupRecyclerView(newsArticles)
                    } else {
                        Toast.makeText(this@StockNewsInfoActivity, "No articles found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@StockNewsInfoActivity, "Failed to fetch news.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsItem.NewsResponse>, t: Throwable) {
                Toast.makeText(this@StockNewsInfoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(newsArticles: List<NewsItem.Article>) {
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNews.adapter = NewsAdapter(newsArticles)
    }
}

