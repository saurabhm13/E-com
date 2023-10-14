package com.example.shoppingapp.data.loca

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val productId: Int,
    val categoryId: Int,
    val categoryName: String,
    val productName: String,
    val icon: String,
    val price: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val quantity: Int
)