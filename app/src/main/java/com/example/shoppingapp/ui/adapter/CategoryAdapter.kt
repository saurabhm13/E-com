package com.example.shoppingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.data.CategoryHome
import com.example.shoppingapp.databinding.CategoryItemsBinding

class CategoryAdapter(
    private val onItemClick: (CategoryHome) -> Unit
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList = ArrayList<CategoryHome>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(categoryList: List<CategoryHome>){
        this.categoryList = categoryList as ArrayList<CategoryHome>
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: CategoryItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.binding.imgCategory.setImageResource(categoryList[position].icon)

        holder.binding.categoryName.text = categoryList[position].name

        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoryList[position])
        }
    }

}