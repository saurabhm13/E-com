package com.example.shoppingapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.ProductRepository
import com.example.shoppingapp.data.loca.ProductDatabase
import com.example.shoppingapp.data.loca.ProductEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteViewModel(
    application: Application
): ViewModel() {

    private val repository: ProductRepository

    private var _favoriteProductLiveData = MutableLiveData<List<ProductEntity>>()
    val favoriteProductLiveData: LiveData<List<ProductEntity>> get() = _favoriteProductLiveData


    init {
        val productDao = ProductDatabase.getInstance(application).productDao()
        repository = ProductRepository(productDao)
    }

    fun getFavoriteProduct() {
        repository.getFavoriteProducts(true)
            .onEach {
                _favoriteProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
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

}