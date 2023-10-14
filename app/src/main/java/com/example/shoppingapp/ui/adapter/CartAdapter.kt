package com.example.shoppingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.data.loca.ProductEntity
import com.example.shoppingapp.databinding.CartItemBinding
import com.example.shoppingapp.databinding.FavoriteItemBinding
import kotlin.math.roundToInt

class CartAdapter(
    private val onItemClick: (ProductEntity) -> Unit,
    private val onAddClick: (ProductEntity) -> Unit,
    private val onMinusClick: (ProductEntity) -> Unit
): RecyclerView.Adapter<CartAdapter.ProductViewHolder>() {

    private var productList = ArrayList<ProductEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCartProductList(productList: List<ProductEntity>) {
        this.productList.clear()
        this.productList = productList as ArrayList<ProductEntity>
        notifyDataSetChanged()
    }

    class ProductViewHolder(val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(productList[position].icon)
            .into(holder.binding.icon)

        holder.binding.name.text = productList[position].productName

        holder.binding.amount.text = "₹"+productList[position].price.toString()

        holder.binding.totalAmount.text = "₹"+("%.1f".format(productList[position].price * productList[position].quantity)).toString()

        holder.binding.quantity.text = productList[position].quantity.toString()

        holder.binding.root.setOnClickListener {
            onItemClick.invoke(productList[position])
        }

        holder.binding.imgAdd.setOnClickListener {
            onAddClick.invoke(productList[position])
        }

        holder.binding.imgMinus.setOnClickListener {
            onMinusClick.invoke(productList[position])
        }
    }

}