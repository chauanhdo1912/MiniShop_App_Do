package com.le_do.minishop.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.le_do.minishop.databinding.ItemCartBinding
import com.le_do.minishop.model.Cart

class CartAdapter (
    private var items: List<Cart>,
    private val onIncrease: (Int) -> Unit,
    private val onDecrease: (Int) -> Unit,
    private val onRemove: (Cart) -> Unit
    ): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
    inner class CartViewHolder(val binding: ItemCartBinding)
         : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvTitle.text = item.product.title
        holder.binding.tvPrice.text = "€${item.product.price}"
        holder.binding.tvQuantity.text = item.quantity.toString()

        Glide.with(holder.itemView)
            .load(item.product.image)
            .into(holder.binding.ivImage)
        holder.binding.btnPlus.setOnClickListener{
            onIncrease(item.product.id)
        }
        holder.binding.btnMinus.setOnClickListener{
            onDecrease(item.product.id)
        }
        holder.binding.btnRemove.setOnClickListener {
            onRemove(item)
        }
    }
    override fun getItemCount() = items.size
    fun updateList(newItems: List<Cart>) {
        items = newItems
        notifyDataSetChanged()
    }
    }