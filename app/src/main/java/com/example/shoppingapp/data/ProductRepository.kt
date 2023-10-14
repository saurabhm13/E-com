package com.example.shoppingapp.data

import androidx.lifecycle.LiveData
import com.example.shoppingapp.data.loca.ProductDao
import com.example.shoppingapp.data.loca.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val productDao: ProductDao
) {

    suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertProducts(products)
    }

    fun getProductsByCategoryId(categoryId: Int): Flow<List<ProductEntity>> {
        return productDao.getProductsByCategoryId(categoryId)
    }

    fun getFavoriteProducts(isFavorite: Boolean): Flow<List<ProductEntity>> {
        return productDao.getFavoriteProducts(isFavorite)
    }

    fun getCartProduct(isInCart: Boolean): Flow<List<ProductEntity>> {
        return productDao.getCartProduct(isInCart)
    }

    suspend fun updateFavorite(isFavorite: Boolean, id: Int) {
        productDao.updateFavorite(isFavorite, id)
    }

    suspend fun updateCart(isInCart: Boolean, id: Int) {
        productDao.updateCart(isInCart, id)
    }

    suspend fun updateQuantity(quantity: Int, id: Int) {
        productDao.updateQuantity(quantity, id)
    }

}