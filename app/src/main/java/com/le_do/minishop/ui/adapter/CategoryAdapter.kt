package com.le_do.minishop.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.le_do.minishop.R
import android.widget.TextView
import com.le_do.minishop.network.ApiService

class CategoryAdapter(
    private val onClick: (String) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categories: List<String> = emptyList()

    fun submitList(newList: List<String>){
        categories = newList
        notifyDataSetChanged()
    }
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category,parent,false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.textView.text = category
        holder.itemView.setOnClickListener {
            onClick(category)
        }
    }
}