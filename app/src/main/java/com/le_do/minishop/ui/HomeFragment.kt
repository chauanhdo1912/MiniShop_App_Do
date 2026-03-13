package com.le_do.minishop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.le_do.minishop.R
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.le_do.minishop.ui.adapter.ProductAdapter
import com.le_do.minishop.viewmodel.HomeViewModel


// Home-Seite der App: zeigt alle Produkte
class HomeFragment : Fragment() {

    // Wird aufgerufen nachdem die View erstellt wurde
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // RecyclerView für die Produktliste
        val rv = view.findViewById<RecyclerView>(R.id.rvProduct)

        // Grid Layout mit 2 Spalten
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        // ViewModel laden
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Produkte aus der API laden
        viewModel.fetchProduct()

        // Beobachtet die Produktliste
        viewModel.fetchProductLiveData.observe(viewLifecycleOwner){ products ->

            // Adapter für die Produkte
            val adapter = ProductAdapter(products) { productId ->

                // Wenn Produkt geklickt wird → zur Detailseite
                val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(productId)
                findNavController().navigate(action)

            }

            // Adapter an RecyclerView setzen
            rv.adapter = adapter

        }

        // Produkte erneut laden
        viewModel.fetchProduct()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Layout der Home-Seite laden
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}