package com.example.stockwiz_prototype.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockwiz_prototype.adapter.FavoriteStocksAdapter
import com.example.stockwiz_prototype.data.FavoriteStock
import com.example.stockwiz_prototype.databinding.FragmentFavoritesBinding
import com.example.stockwiz_prototype.network.OnFavoriteStockClickListener
import com.example.stockwiz_prototype.ui.search.StockInfoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesFragment : Fragment(), OnFavoriteStockClickListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val userUID: String? by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userUID?.let { uid ->
            fetchFavoriteStocks(uid)
        }
    }

    private fun setupRecyclerView(favoriteStocks: List<FavoriteStock>) {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorites.layoutManager = layoutManager
        binding.recyclerViewFavorites.adapter = FavoriteStocksAdapter(favoriteStocks, this)
    }


    override fun onFavoriteStockClicked(stockSymbol: String) {
        // Implementation of navigateToStockInfo function
        navigateToStockInfo(stockSymbol)
    }

    private fun fetchFavoriteStocks(uid: String) {
        FirebaseFirestore.getInstance().collection("users").document(uid)
            .collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val favoriteStocks = documents.mapNotNull { it.toObject(FavoriteStock::class.java) }
                Log.d("FavoritesFragment", "Number of favorites: ${favoriteStocks.size}")
                setupRecyclerView(favoriteStocks)
            }
            .addOnFailureListener { exception ->
                Log.e("FavoritesFragment", "Error fetching favorites: ${exception.message}")
            }
    }


    private fun navigateToStockInfo(stockSymbol: String) {
        val intent = Intent(activity, StockInfoActivity::class.java).apply {
            putExtra("stock_symbol", stockSymbol)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
