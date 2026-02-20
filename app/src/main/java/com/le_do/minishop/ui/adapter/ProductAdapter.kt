package com.le_do.minishop.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.le_do.minishop.R
import com.le_do.minishop.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter (
    private val products: List<Product>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = products.size
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvTitle.text = product.title
        val format = NumberFormat.getCurrencyInstance(Locale.GERMANY)
        holder.tvPrice.text = format.format(product.price)

        Glide.with(holder.itemView.context)
            .load(product.image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.ivImage)
        holder.itemView.setOnClickListener {
            onItemClick(product.id)
        }
    }
}




