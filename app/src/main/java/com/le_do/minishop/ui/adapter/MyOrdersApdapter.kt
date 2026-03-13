package com.le_do.minishop.ui.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.le_do.minishop.R
import com.le_do.minishop.data.local.entity.OrderEntity
import java.text.SimpleDateFormat
import java.util.*

class MyOrdersAdapter(
    private val orders: List<OrderEntity>
) : RecyclerView.Adapter<MyOrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate: TextView = view.findViewById(R.id.tvOrderDate)
        val tvTotal: TextView = view.findViewById(R.id.tvOrderTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.tvDate.text = sdf.format(Date(order.createdAt))
        holder.tvTotal.text = "Total: €${String.format("%.2f", order.totalPrice)}"
    }
}