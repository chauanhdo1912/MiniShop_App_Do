package com.le_do.minishop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.le_do.minishop.R
import com.le_do.minishop.viewmodel.CartViewModel
import com.le_do.minishop.databinding.FragmentCartBinding
import  android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.le_do.minishop.ui.adapter.CartAdapter
import androidx.navigation.fragment.findNavController

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Quan sát LiveData cartItems và set Adapter
        cartViewModel.cartItems.observe(viewLifecycleOwner) { list ->
            binding.rvCart.adapter = CartAdapter(
                items = list,
                onIncrease = { cartViewModel.increase(it) },
                onDecrease = { cartViewModel.decrease(it) },
                onRemove = { cartViewModel.removeFromCart(it.product.id) }
            )
        }
        cartViewModel.totalPrice.observe(viewLifecycleOwner){ total ->
        binding.tvTotalPrice.text = "Total Price: €${String.format("%.2f",total)}"
        }
        binding.toolbarCart.setNavigationIcon(R.drawable.ic_back) // icon mũi tên back
        binding.toolbarCart.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())

        // Nút Checkout
        binding.btnCheckout.setOnClickListener {
            Toast.makeText(requireContext(), "Checkout successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
