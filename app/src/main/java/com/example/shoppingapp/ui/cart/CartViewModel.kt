package com.example.shoppingapp.ui.cart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.ProductRepository
import com.example.shoppingapp.data.loca.ProductDatabase
import com.example.shoppingapp.data.loca.ProductEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CartViewModel(
    application: Application
): ViewModel() {

    private val repository: ProductRepository

    private var _cartProductLiveData = MutableLiveData<List<ProductEntity>>()
    val cartProductLiveData: LiveData<List<ProductEntity>> get() = _cartProductLiveData

    init {
        val productDao = ProductDatabase.getInstance(application).productDao()
        repository = ProductRepository(productDao)
    }

    fun getCartProduct() {
        repository.getCartProduct(true)
            .onEach {
                _cartProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
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

    fun removeFromCart() {
        repository.getCartProduct(true)
            .onEach {
                for (product in it) {
                    updateCart(true, product.id)
                    updateQuantity(0, product.id)
                }
                _cartProductLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

}