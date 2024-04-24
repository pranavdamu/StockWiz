package com.example.stockwiz_prototype.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockwiz_prototype.adapter.NewsAdapter
import com.example.stockwiz_prototype.databinding.FragmentDashboardBinding
import com.example.stockwiz_prototype.model.NewsItem
import com.example.stockwiz_prototype.network.RetrofitClient
import com.example.stockwiz_prototype.ui.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // Load news articles with hardcoded query "Stock Market"
        loadNews()  // Replace with your actual API key

        setupSearchBarClickListener()

        return binding.root
    }

    private fun loadNews() {
        val query = "Stock Market United States"
        val apiKey = "a7ba9917efb44c2fa56b207540168f9b"
        RetrofitClient.instance2.getNewsArticles(query, apiKey = apiKey).enqueue(object : Callback<NewsItem.NewsResponse> {
            override fun onResponse(call: Call<NewsItem.NewsResponse>, response: Response<NewsItem.NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val newsItems = response.body()!!.articles
                    if (newsItems.isNotEmpty()) {
                        setupRecyclerView(newsItems)
                    } else {
                        Toast.makeText(context, "No articles found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsItem.NewsResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




    private fun setupRecyclerView(newsArticles: List<NewsItem.Article>) {
        binding.newsRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.newsRecyclerview.adapter = NewsAdapter(newsArticles)
    }

    private fun setupSearchBarClickListener() {
        binding.searchBar.setOnClickListener {
            navigateToSearchPage()
        }
    }

    private fun navigateToSearchPage() {
        val intent = Intent(activity, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
