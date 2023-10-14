package com.example.shoppingapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityFavoriteBinding
import com.example.shoppingapp.ui.adapter.FavoriteAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels { FavoriteViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        viewModel.getFavoriteProduct()
        prepareRecyclerView()

    }

    private fun prepareRecyclerView() {
        val favoriteAdapter = FavoriteAdapter(
            onItemClick = {

            },
            onFavoriteClick = {
                viewModel.updateFavorite(it.isFavorite, it.id)
            },
            onAddClick = {
                viewModel.updateCart(it.isInCart, it.id)
                viewModel.updateQuantity(1, it.id)
            },
            onMinusClick = {
                viewModel.updateCart(it.isInCart, it.id)
                viewModel.updateQuantity(0, it.id)
            }
        )

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        viewModel.favoriteProductLiveData.observe(this) {
            favoriteAdapter.setFavoriteProductList(it)
        }
    }
}