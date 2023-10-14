package com.example.e_com.data

data class Category(
    val id: Int,
    val name: String,
    val items: List<Product>
)