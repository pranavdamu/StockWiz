package com.example.stockwiz_prototype.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockwiz_prototype.adapter.NewsAdapter
import com.example.stockwiz_prototype.databinding.FragmentDashboardBinding
import com.example.stockwiz_prototype.model.NewsItem
import com.example.stockwiz_prototype.network.NewsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.stockwiz_prototype.ui.search.SearchActivity
import android.content.Intent


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter
    private lateinit var retrofit: Retrofit
    private lateinit var newsApiService: NewsApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // Set up RecyclerView and LayoutManager
        binding.newsRecyclerview.layoutManager = LinearLayoutManager(context)

        // Initialize Retrofit
        retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsApiService = retrofit.create(NewsApiService::class.java)

        // Load news articles
        loadNews("us", "business","yuourapikey")  // Replace with your actual API key

        setupRecyclerView()
        setupSearchBarClickListener()

        return binding.root
    }

    private fun loadNews(country: String,category:String, apiKey: String) {
        newsApiService.getTopHeadlines(country,category,apiKey).enqueue(object : Callback<NewsItem.NewsResponse> {
            override fun onResponse(call: Call<NewsItem.NewsResponse>, response: Response<NewsItem.NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val newsItems = response.body()!!.articles
                    adapter = NewsAdapter(newsItems)
                    binding.newsRecyclerview.adapter = adapter
                } else {
                    Toast.makeText(context, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsItem.NewsResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setupRecyclerView() {
        binding.newsRecyclerview.layoutManager = LinearLayoutManager(context)
        // Initialization and setting adapter for RecyclerView might go here
    }

    private fun setupSearchBarClickListener() {
        binding.searchBar.setOnClickListener {
            navigateToSearchPage()
        }
    }

    private fun navigateToSearchPage() {
        // Example: Start an Activity for search
        val intent = Intent(activity, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
