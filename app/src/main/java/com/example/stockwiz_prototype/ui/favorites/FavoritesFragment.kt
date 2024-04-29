package com.example.stockwiz_prototype.ui.favorites
import com.example.stockwiz_prototype.R

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

/*class FavoritesFragment : Fragment(), OnFavoriteStockClickListener {

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
}*/



import android.widget.TextView



/*class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val userUID: String? by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun fetchFavoriteStocks(uid: String) {
        FirebaseFirestore.getInstance().collection("users").document(uid)
            .collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val favoriteStocks = documents.mapNotNull { it.toObject(FavoriteStock::class.java) }
                Log.d("FavoritesFragment", "Number of favorites: ${favoriteStocks.size}")
                displayFavoriteStocksInXmlTemplate(favoriteStocks)
            }
            .addOnFailureListener { exception ->
                Log.e("FavoritesFragment", "Error fetching favorites: ${exception.message}")
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userUID?.let { uid ->
            fetchFavoriteStocks(uid)
        }
    }

    private fun displayFavoriteStocksInXmlTemplate(favoriteStocks: List<FavoriteStock>) {
        // Assuming you have a TextView named 'favoritesTextView' in your XML template
        val favoritesTextView = requireView().findViewById<TextView>(R.id.favoritesTextView)
        val stringBuilder = StringBuilder()
        for (favoriteStock in favoriteStocks) {
            stringBuilder.append("${favoriteStock.symbol}\n") // Only display symbol
        }
        favoritesTextView.text = stringBuilder.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/




import androidx.recyclerview.widget.RecyclerView

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<ViewHolder>

    private val userUID: String? by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun fetchFavoriteStocks(uid: String) {
        FirebaseFirestore.getInstance().collection("users").document(uid)
            .collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val favoriteStocks = documents.mapNotNull { it.toObject(FavoriteStock::class.java) }
                Log.d("FavoritesFragment", "Number of favorites: ${favoriteStocks.size}")
                displayFavoriteStocksInRecyclerView(favoriteStocks)
            }
            .addOnFailureListener { exception ->
                Log.e("FavoritesFragment", "Error fetching favorites: ${exception.message}")
            }
    }

    private fun setupRecyclerView(favoriteStocks: List<FavoriteStock>) {
        adapter = object : RecyclerView.Adapter<ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_stock, parent, false)
                return ViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val favoriteStock = favoriteStocks[position]
                holder.bind(favoriteStock)
            }

            override fun getItemCount(): Int {
                return favoriteStocks.size
            }
        }

        recyclerView = binding.recyclerViewFavorites
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayFavoriteStocksInRecyclerView(favoriteStocks: List<FavoriteStock>) {
        setupRecyclerView(favoriteStocks)
    }

    private fun navigateToStockInfo(stockSymbol: String) {
        // Navigate to the stock info activity or perform any other action
        val intent = Intent(requireContext(), StockInfoActivity::class.java).apply {
            putExtra("stock_symbol", stockSymbol)
        }
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userUID?.let { uid ->
            fetchFavoriteStocks(uid)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val symbolTextView: TextView = itemView.findViewById(R.id.textViewSymbol)

        fun bind(favoriteStock: FavoriteStock) {
            // Bind data to views
            symbolTextView.text = favoriteStock.symbol

            // Set click listener
            itemView.setOnClickListener {
                navigateToStockInfo(favoriteStock.symbol)
            }
        }
    }


}
