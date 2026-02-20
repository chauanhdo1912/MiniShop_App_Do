package com.le_do.minishop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.le_do.minishop.R
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.le_do.minishop.ui.adapter.ProductAdapter
import com.le_do.minishop.viewmodel.HomeViewModel
import androidx.fragment.app.activityViewModels
import com.le_do.minishop.viewmodel.CartViewModel
class HomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        val rv = view.findViewById<RecyclerView>(R.id.rvProduct)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.fetchProduct()

        viewModel.fetchProductLiveData.observe(viewLifecycleOwner){ products ->
            val adapter = ProductAdapter(products) { productId ->
                val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(productId)
                findNavController().navigate(action)

            }
            rv.adapter = adapter

        }
        viewModel.fetchProduct()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}