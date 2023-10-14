package com.example.shoppingapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.data.CategoryHome
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.ui.adapter.CategoryAdapter
import com.example.shoppingapp.ui.adapter.ProductAdapter
import com.example.shoppingapp.ui.cart.CartActivity
import com.example.shoppingapp.ui.category.CategoryProductActivity
import com.example.shoppingapp.ui.favorite.FavoriteActivity
import com.example.shoppingapp.util.JsonFileReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory(application) }

    private val categoryList = ArrayList<CategoryHome>()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!viewModel.isDatabaseExists(this@MainActivity, "product.db")) {
            CoroutineScope(Dispatchers.Main).launch {
                val categories = JsonFileReader().readAndParseJson(applicationContext, R.raw.shopping)
                viewModel.parseJsonAndInsert(categories)
            }
        }

        prepareCategoryRecyclerView()
        createCategoryList()
        categoryAdapter.setCategoryList(categoryList)

        prepareFoodRecyclerView()
        prepareBeverageRecyclerView()
        prepareHygieneEssentialsRecyclerView()
        preparePoojaNeedsRecyclerView()
        prepareElectronicItemsRecyclerView()

        binding.imgCart.setOnClickListener {
            val intoCart = Intent(this, CartActivity::class.java)
            startActivity(intoCart)
        }

        binding.imgFavorite.setOnClickListener {
            val intoFavorite = Intent(this, FavoriteActivity::class.java)
            startActivity(intoFavorite)
        }

        binding.foodMore.setOnClickListener {
            if (binding.rvFood.isVisible) {
                binding.rvFood.visibility = View.GONE
                binding.foodMore.setImageResource(R.drawable.ic_arrow_down)
            }else {
                binding.rvFood.visibility = View.VISIBLE
                binding.foodMore.setImageResource(R.drawable.ic_arrow_up)
            }
        }

        binding.hygieneEssentialsMore.setOnClickListener {
            if (binding.rvHygieneEssentials.isVisible) {
                binding.rvHygieneEssentials.visibility = View.GONE
                binding.hygieneEssentialsMore.setImageResource(R.drawable.ic_arrow_down)
            }else {
                binding.rvHygieneEssentials.visibility = View.VISIBLE
                binding.hygieneEssentialsMore.setImageResource(R.drawable.ic_arrow_up)
            }
        }

        binding.poojaDailyNeedsMore.setOnClickListener {
            if (binding.rvPoojaDailyNeeds.isVisible) {
                binding.rvPoojaDailyNeeds.visibility = View.GONE
                binding.poojaDailyNeedsMore.setImageResource(R.drawable.ic_arrow_down)
            }else {
                binding.rvPoojaDailyNeeds.visibility = View.VISIBLE
                binding.poojaDailyNeedsMore.setImageResource(R.drawable.ic_arrow_up)
            }
        }

        binding.beveragesMore.setOnClickListener {
            if (binding.rvBeverages.isVisible) {
                binding.rvBeverages.visibility = View.GONE
                binding.beveragesMore.setImageResource(R.drawable.ic_arrow_down)
            }else {
                binding.rvBeverages.visibility = View.VISIBLE
                binding.beveragesMore.setImageResource(R.drawable.ic_arrow_up)
            }
        }

        binding.electronicItemsMore.setOnClickListener {
            if (binding.rvElectronicItems.isVisible) {
                binding.rvElectronicItems.visibility = View.GONE
                binding.electronicItemsMore.setImageResource(R.drawable.ic_arrow_down)
            }else {
                binding.rvElectronicItems.visibility = View.VISIBLE
                binding.electronicItemsMore.setImageResource(R.drawable.ic_arrow_up)
            }
        }

    }

    private fun prepareCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter {
            val intoProductByCategory = Intent(this, CategoryProductActivity::class.java)
            intoProductByCategory.putExtra("categoryId", it.id.toString())
            intoProductByCategory.putExtra("categoryName", it.name)
            startActivity(intoProductByCategory)
        }
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun createCategoryList() {

        categoryList.add(CategoryHome(55, R.drawable.food, "Food"))
        categoryList.add(CategoryHome(56, R.drawable.beverages, "Beverages"))
        categoryList.add(CategoryHome(57, R.drawable.hygiene, "Hygiene Essential"))
        categoryList.add(CategoryHome(58, R.drawable.pooja, "Pooja Daily Needs"))
        categoryList.add(CategoryHome(59, R.drawable.electronic, "Electronic Items"))
    }

    private fun prepareFoodRecyclerView() {
        viewModel.getFoodProducts(55)

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

        binding.rvFood.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }

        viewModel.foodProductLiveData.observe(this) {
            productAdapter.setProductList(it)
        }

        viewModel.cartCountLiveData.observe(this) {count ->
            if (count in 1..9) {
                binding.cartCount.text = count.toString()
                binding.cartCount.visibility = View.VISIBLE
            }else if (count >= 10) {
                binding.cartCount.text = "9+"
                binding.cartCount.visibility = View.VISIBLE
            }else if (count == 0) {
                binding.cartCount.visibility = View.GONE
            }
        }

        viewModel.favoriteCountLiveData.observe(this) {count ->
            if (count in 1..9) {
                binding.favoriteCount.text = count.toString()
                binding.favoriteCount.visibility = View.VISIBLE
            }else if (count >= 10) {
                binding.favoriteCount.text = "9+"
                binding.favoriteCount.visibility = View.VISIBLE
            }else if (count == 0) {
                binding.favoriteCount.visibility = View.GONE
            }
        }
    }

    private fun prepareBeverageRecyclerView() {
        viewModel.getBeveragesProducts(56)

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

        binding.rvBeverages.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }

        viewModel.beveragesProductLiveData.observe(this) {
            productAdapter.setProductList(it)
        }
    }

    private fun prepareHygieneEssentialsRecyclerView() {
        viewModel.getHygienicNeedsProducts(57)

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

        binding.rvHygieneEssentials.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }

        viewModel.hygieneEssProductLiveData.observe(this) {
            productAdapter.setProductList(it)
        }
    }

    private fun preparePoojaNeedsRecyclerView() {
        viewModel.getPoojaNeedsProducts(58)

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

        binding.rvPoojaDailyNeeds.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }

        viewModel.poojaNeedsProductLiveData.observe(this) {
            productAdapter.setProductList(it)
        }
    }

    private fun prepareElectronicItemsRecyclerView() {
        viewModel.getElectronicProducts(59)

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

        binding.rvElectronicItems.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }

        viewModel.electronicProductLiveData.observe(this) {
            productAdapter.setProductList(it)
        }
    }
}