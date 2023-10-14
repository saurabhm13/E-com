package com.example.shoppingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.data.loca.ProductEntity
import com.example.shoppingapp.databinding.FavoriteItemBinding

class FavoriteAdapter(
    private val onItemClick: (ProductEntity) -> Unit,
    private val onFavoriteClick: (ProductEntity) -> Unit,
    private val onAddClick: (ProductEntity) -> Unit,
    private val onMinusClick: (ProductEntity) -> Unit
): RecyclerView.Adapter<FavoriteAdapter.ProductViewHolder>() {

    private var productList = ArrayList<ProductEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteProductList(productList: List<ProductEntity>) {
        this.productList.clear()
        this.productList = productList as ArrayList<ProductEntity>
        notifyDataSetChanged()
    }

    class ProductViewHolder(val binding: FavoriteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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

        holder.binding.root.setOnClickListener {
            onItemClick.invoke(productList[position])
        }

        holder.binding.imgFavorite.setOnClickListener {
            onFavoriteClick.invoke(productList[position])
        }

        holder.binding.imgAdd.setOnClickListener {
            onAddClick.invoke(productList[position])
        }

        holder.binding.imgMinus.setOnClickListener {
            onMinusClick.invoke(productList[position])
        }

        if (productList[position].isInCart) {
            holder.binding.imgMinus.visibility = View.VISIBLE
            holder.binding.imgAdd.visibility = View.GONE
        }else {
            holder.binding.imgMinus.visibility = View.GONE
            holder.binding.imgAdd.visibility = View.VISIBLE
        }
    }

}