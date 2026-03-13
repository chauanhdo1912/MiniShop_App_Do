package com.le_do.minishop.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.le_do.minishop.databinding.FragmentMyOrdersBinding
import com.le_do.minishop.data.local.AppDatabase
import com.le_do.minishop.data.local.OrderRepository
import com.le_do.minishop.viewmodel.OrderViewModel
import com.le_do.minishop.viewmodel.OrderViewModelFactory
import com.le_do.minishop.utils.SessionManager
import com.le_do.minishop.ui.adapter.MyOrdersAdapter
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.R

class MyOrdersFragment : Fragment() {

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMyOrders.title = "My Orders"
        binding.toolbarMyOrders.setNavigationIcon(R.drawable.ic_back)
        binding.toolbarMyOrders.setNavigationOnClickListener { findNavController().navigateUp() }

        val db = AppDatabase.getInstance(requireContext())
        val repository = OrderRepository(db.OrderDao())
        val factory = OrderViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]

        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())

        val email = SessionManager(requireContext()).getEmail()
        viewModel.loadOrders(email)

        viewModel.orders.observe(viewLifecycleOwner) { list ->
            binding.rvOrders.adapter = MyOrdersAdapter(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}