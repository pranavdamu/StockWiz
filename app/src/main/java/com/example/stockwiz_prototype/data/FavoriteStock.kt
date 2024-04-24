package com.example.stockwiz_prototype.data

import com.google.firebase.firestore.PropertyName

data class FavoriteStock(
    @PropertyName("symbol") val symbol: String = ""  // Provide a default value
    // Add other fields with default values if necessary
)
