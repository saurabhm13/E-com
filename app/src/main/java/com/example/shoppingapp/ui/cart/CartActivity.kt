package com.example.shoppingapp.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ActivityCartBinding
import com.example.shoppingapp.ui.adapter.CartAdapter
import kotlin.math.round

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private val viewModel: CartViewModel by viewModels { CartViewModelFactory(application) }

    var subTotal = 0.0
    var discount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        viewModel.getCartProduct()
        prepareRecyclerView()

        binding.btnCheckout.setOnClickListener {
            viewModel.removeFromCart()
            Toast.makeText(this, "Order Completed", Toast.LENGTH_LONG).show()
        }

    }

    private fun prepareRecyclerView() {
        subTotal = 0.0
        val cartAdapter = CartAdapter(
            onItemClick = {

            },
            onAddClick = {
                viewModel.updateQuantity(it.quantity+1, it.id)
                subTotal = 0.0
            },
            onMinusClick = {
                if (it.quantity == 1) {
                    viewModel.updateCart(it.isInCart, it.id)
                    viewModel.updateQuantity(0, it.id)
                }else {
                    viewModel.updateQuantity(it.quantity-1, it.id)
                }
                subTotal = 0.0
            }
        )

        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }

        viewModel.cartProductLiveData.observe(this) {
            subTotal = 0.0
            cartAdapter.setCartProductList(it)
            for (product in it) {
                subTotal += product.price * product.quantity
            }
            binding.subtotal.text = ("%.1f".format(subTotal))
            discount = (round(subTotal/10))
            binding.discount.text = discount.toString()
            binding.total.text = ("%.1f".format(subTotal-discount))
        }
    }
}