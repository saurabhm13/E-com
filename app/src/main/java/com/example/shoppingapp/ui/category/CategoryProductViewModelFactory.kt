package com.example.shoppingapp.ui.category

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CategoryProductViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryProductViewModel::class.java)) {
            return CategoryProductViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}