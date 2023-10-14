package com.example.shoppingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.data.loca.ProductEntity
import com.example.shoppingapp.databinding.ProductItemBinding

class ProductAdapter(
    private val onItemClick: (ProductEntity) -> Unit,
    private val onFavoriteClick: (ProductEntity) -> Unit,
    private val onAddClick: (ProductEntity) -> Unit,
    private val onMinusClick: (ProductEntity) -> Unit
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList = ArrayList<ProductEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(productList: List<ProductEntity>) {
        this.productList.clear()
        this.productList = productList as ArrayList<ProductEntity>
        notifyDataSetChanged()
    }

    class ProductViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(productList[position].icon)
            .into(holder.binding.icon)

        holder.binding.name.text = productList[position].productName

        holder.binding.price.text = productList[position].price.toString()

        if (productList[position].isFavorite) {
            holder.binding.imgFavorite.setImageResource(R.drawable.ic_favorite)
        }else {
            holder.binding.imgFavorite.setImageResource(R.drawable.ic_favorite_border)
        }

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