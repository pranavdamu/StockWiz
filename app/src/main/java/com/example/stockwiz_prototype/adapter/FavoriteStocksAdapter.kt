package com.example.stockwiz_prototype.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stockwiz_prototype.R
import com.example.stockwiz_prototype.data.FavoriteStock
import com.example.stockwiz_prototype.network.OnFavoriteStockClickListener

class FavoriteStocksAdapter(
    private val favoriteStocks: List<FavoriteStock>,
    private val clickListener: OnFavoriteStockClickListener
) : RecyclerView.Adapter<FavoriteStocksAdapter.FavoriteStockViewHolder>() {

    inner class FavoriteStockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewSymbol: TextView = itemView.findViewById(R.id.text_view_symbol)

        init {
            itemView.setOnClickListener {
                clickListener.onFavoriteStockClicked(favoriteStocks[adapterPosition].symbol)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        return FavoriteStockViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteStockViewHolder, position: Int) {
        val favoriteStock = favoriteStocks[position]
        holder.textViewSymbol.text = favoriteStock.symbol
    }

    override fun getItemCount(): Int {
        return favoriteStocks.size
    }
}
