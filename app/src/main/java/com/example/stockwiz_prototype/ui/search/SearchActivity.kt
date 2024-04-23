package com.example.stockwiz_prototype.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stockwiz_prototype.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch
import com.example.stockwiz_prototype.data.NasdaqDownloader
import android.text.Editable
import android.text.TextWatcher

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchBar()
        fetchStockNames()
        setupListView()
    }
    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that, within s, the count characters
                // beginning at start are about to be replaced by new text with length after.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that, within s, the count characters
                // beginning at start have just replaced old text that had length before.
                adapter?.filter?.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called to notify you that, somewhere within s, the text has been changed.
                // It is legitimate to make further changes to s from this callback, but be careful not to get yourself into an infinite loop,
                // because any changes you make will cause this method to be called again recursively.
            }
        })
    }

    private fun setupListView() {
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val stockSymbol = parent.adapter.getItem(position) as String
            val intent = Intent(this, StockInfoActivity::class.java).apply {
                putExtra("stock_symbol", stockSymbol)
            }
            startActivity(intent)
        }
    }

    private fun fetchStockNames() {
        binding.progressBar.visibility = View.VISIBLE // Show loading indicator
        lifecycleScope.launch {
            val stockNames = NasdaqDownloader.downloadStockList()
            binding.progressBar.visibility = View.GONE // Hide loading indicator
            if (stockNames != null) {
                adapter = ArrayAdapter(this@SearchActivity, android.R.layout.simple_list_item_1, stockNames)
                binding.listView.adapter = adapter
            } else {
                Toast.makeText(this@SearchActivity, "Failed to load stock names.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
