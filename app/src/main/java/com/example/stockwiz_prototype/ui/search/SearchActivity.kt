package com.example.stockwiz_prototype.ui.search

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stockwiz_prototype.databinding.ActivitySearchBinding // Make sure this import matches your layout file name
import kotlinx.coroutines.launch
import com.example.stockwiz_prototype.data.NasdaqDownloader

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchStockNames()
    }

    private fun fetchStockNames() {
        binding.progressBar.visibility = View.VISIBLE // Show loading indicator
        lifecycleScope.launch {
            val stockNames = NasdaqDownloader.downloadStockList()
            binding.progressBar.visibility = View.GONE // Hide loading indicator
            stockNames?.let {
                val adapter = ArrayAdapter(this@SearchActivity, R.layout.simple_list_item_1, it)
                binding.listView.adapter = adapter
            } ?: run {
                Toast.makeText(this@SearchActivity, "Failed to load stock names.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
