package com.example.producthub.data.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val isFavorite: Boolean = false
)
