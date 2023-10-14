package com.example.shoppingapp.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityCategoryProductBinding
import com.example.shoppingapp.ui.adapter.ProductAdapter

class CategoryProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryProductBinding
    private val viewModel: CategoryProductViewModel by viewModels { CategoryProductViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getStringExtra("categoryId")
        val categoryName = intent.getStringExtra("categoryName")

        binding.categoryName.text = categoryName

        binding.back.setOnClickListener {
            finish()
        }

        if (categoryId != null) {
            viewModel.getProductByCategory(categoryId = categoryId.toInt())
        }
        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {
        val productAdapter = ProductAdapter(
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

        binding.rvProduct.apply {
            layoutManager = GridLayoutManager(this@CategoryProductActivity, 2, GridLayoutManager.VERTICAL, false)
            adapter = productAdapter
        }

        viewModel.categoryProductLiveData.observe(this) {
            productAdapter.setProductList(it)
        }
    }
}