package com.example.shoppingapp.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_com.data.Category
import com.example.shoppingapp.data.ProductRepository
import com.example.shoppingapp.data.loca.ProductDatabase
import com.example.shoppingapp.data.loca.ProductEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application
): ViewModel() {

    private val repository: ProductRepository

    private var _foodProductLiveData = MutableLiveData<List<ProductEntity>>()
    val foodProductLiveData: LiveData<List<ProductEntity>> get() = _foodProductLiveData

    private var _beveragesProductLiveData = MutableLiveData<List<ProductEntity>>()
    val beveragesProductLiveData: LiveData<List<ProductEntity>> get() = _beveragesProductLiveData

    private var _hygieneEssProductLiveData = MutableLiveData<List<ProductEntity>>()
    val hygieneEssProductLiveData: LiveData<List<ProductEntity>> get() = _hygieneEssProductLiveData

    private var _poojaNeedsProductLiveData = MutableLiveData<List<ProductEntity>>()
    val poojaNeedsProductLiveData: LiveData<List<ProductEntity>> get() = _poojaNeedsProductLiveData

    private var _electronicProductLiveData = MutableLiveData<List<ProductEntity>>()
    val electronicProductLiveData: LiveData<List<ProductEntity>> get() = _electronicProductLiveData

    private var _favoriteCountLiveData = MutableLiveData<Int>()
    val favoriteCountLiveData: LiveData<Int> get() = _favoriteCountLiveData

    private var _cartCountLiveData = MutableLiveData<Int>()
    val cartCountLiveData: LiveData<Int> get() = _cartCountLiveData


    init {
        val productDao = ProductDatabase.getInstance(application).productDao()
        repository = ProductRepository(productDao)
        countCartProduct()
        countFavoriteProduct()
    }

    suspend fun parseJsonAndInsert(categories: List<Category>) {
        val productEntities = mutableListOf<ProductEntity>()

        for (category in categories) {
            for (product in category.items) {
                productEntities.add(
                    ProductEntity(
                        id = product.id,
                        productId = product.id,
                        categoryName = category.name,
                        productName = product.name,
                        icon = product.icon,
                        categoryId = category.id,
                        price = product.price,
                        isFavorite = false,
                        isInCart = false,
                        quantity = 0
                    )
                )
            }
        }

        repository.insertProducts(productEntities)
    }

    fun updateFavorite(isFavorite: Boolean, id: Int) {
        viewModelScope.launch {
            repository.updateFavorite(!isFavorite, id)
        }
    }

    fun updateCart(isInCart: Boolean, id: Int) {
        viewModelScope.launch {
            repository.updateCart(!isInCart, id)
        }
    }

    fun updateQuantity(quantity: Int, id: Int) {
        viewModelScope.launch {
            repository.updateQuantity(quantity, id)
        }
    }

    private fun countFavoriteProduct() {
        repository.getFavoriteProducts(true)
            .onEach {
                _favoriteCountLiveData.postValue(it.count())
            }
            .launchIn(viewModelScope)
    }

    private fun countCartProduct() {
        repository.getCartProduct(true)
            .onEach {
                _cartCountLiveData.postValue(it.count())
            }
            .launchIn(viewModelScope)
    }

    fun getFoodProducts(categoryId: Int){
        repository.getProductsByCategoryId(categoryId)
            .onEach {
                _foodProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    fun getBeveragesProducts(categoryId: Int){
        repository.getProductsByCategoryId(categoryId)
            .onEach {
                _beveragesProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    fun getHygienicNeedsProducts(categoryId: Int){
        repository.getProductsByCategoryId(categoryId)
            .onEach {
                _hygieneEssProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    fun getPoojaNeedsProducts(categoryId: Int){
        repository.getProductsByCategoryId(categoryId)
            .onEach {
                _poojaNeedsProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    fun getElectronicProducts(categoryId: Int){
        repository.getProductsByCategoryId(categoryId)
            .onEach {
                _electronicProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    fun isDatabaseExists(context: Context, databaseName: String): Boolean {
        val databaseFile = context.getDatabasePath(databaseName)
        return databaseFile.exists()
    }

}