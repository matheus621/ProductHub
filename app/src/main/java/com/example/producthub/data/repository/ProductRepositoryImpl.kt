package com.example.producthub.data.repository

import com.example.producthub.data.local.ProductDao
import com.example.producthub.data.local.ProductEntity
import com.example.producthub.data.model.Product
import com.example.producthub.data.model.toDomain
import com.example.producthub.data.model.toEntity
import com.example.producthub.data.remote.ProductApiService
import com.example.producthub.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService,
    private val dao: ProductDao
) : ProductRepository {


    override fun getProducts(): Flow<List<Product>> = flow {
        val localProducts = dao.getAllProducts().map { list -> list.map { it.toDomain() } }
        emitAll(localProducts)

        val remoteProducts = api.getProducts()
        dao.insertAllProducts(remoteProducts.map { it.toEntity() })
    }

    override suspend fun addProduct(product: Product) {
        val created = api.createProduct(product)
        dao.insertProduct(created.toEntity())
    }

    override suspend fun addAllProducts(products: List<Product>) {
        val created = products.map { api.createProduct(it).toEntity() }
        dao.insertAllProducts(created)
    }

    override suspend fun updateProduct(product: Product) {
        api.updateProduct(product.id, product)
        dao.updateProduct(product.toEntity())
    }

    override suspend fun deleteProduct(product: Product) {
        api.deleteProduct(product.id)
        dao.delete(product.toEntity())
    }
}