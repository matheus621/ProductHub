package com.example.producthub.data.model

import com.example.producthub.data.local.ProductEntity

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        price = price,
        quantity = quantity,
        imageUrl = imageUrl,
        isFavorite = isFavorite
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        quantity = quantity,
        imageUrl = imageUrl,
        isFavorite = isFavorite
    )
}