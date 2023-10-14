package com.example.shoppingapp.data.loca

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM productentity WHERE categoryId = :categoryId")
    fun getProductsByCategoryId(categoryId: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM productentity WHERE isFavorite = :isFavorite")
    fun getFavoriteProducts(isFavorite: Boolean): Flow<List<ProductEntity>>

    @Query("SELECT * FROM productentity WHERE isInCart = :isInCart")
    fun getCartProduct(isInCart: Boolean): Flow<List<ProductEntity>>

    @Query("UPDATE productentity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(isFavorite: Boolean, id: Int)

    @Query("UPDATE productentity SET isInCart = :isInCart WHERE id = :id")
    suspend fun updateCart(isInCart: Boolean, id: Int)

    @Query("UPDATE productentity SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(quantity: Int, id: Int)

}