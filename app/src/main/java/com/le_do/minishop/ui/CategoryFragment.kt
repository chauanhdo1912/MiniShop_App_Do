package com.le_do.minishop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.le_do.minishop.R
import com.le_do.minishop.data.local.ProductRepository
import com.le_do.minishop.viewmodel.CategoryViewModel
import com.le_do.minishop.network.RetrofitClient.apiService
import com.le_do.minishop.ui.adapter.CategoryAdapter
import com.le_do.minishop.ui.adapter.ProductAdapter
import com.le_do.minishop.viewmodel.CategoryViewModelFactory

// Fragment für Kategorien und Produkte
class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private var isShowingProducts = false

    private lateinit var toolbar: MaterialToolbar

    // Layout laden
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    // UI und Daten initialisieren
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel mit Repository erstellen
        val repository = ProductRepository(apiService)
        val factory = CategoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(CategoryViewModel::class.java)

        // RecyclerView für Kategorien
        recyclerView = view.findViewById(R.id.rvCategories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter für Kategorien
        categoryAdapter = CategoryAdapter { categoryName ->
            viewModel.fetchProductByCategory(categoryName)
        }

        recyclerView.adapter = categoryAdapter

        // Daten beobachten
        observeViewModel()

        // Kategorien laden
        viewModel.fetchCategories()

        // Toolbar konfigurieren
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.navigationIcon = null
        toolbar.title = "Category"

        // Verhalten des Back Buttons in der Toolbar
        toolbar.setNavigationOnClickListener {

            if (isShowingProducts ){
                recyclerView.adapter = categoryAdapter
                toolbar.navigationIcon = null
                toolbar.title = "Category"
                isShowingProducts = false

            }else{
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    // Beobachtet Änderungen im ViewModel
    private fun observeViewModel(){

        // Kategorien anzeigen
        viewModel.categories.observe(viewLifecycleOwner){ list ->
            categoryAdapter.submitList(list)
        }

        // Produkte einer Kategorie anzeigen
        viewModel.products.observe(viewLifecycleOwner){ productList ->

            val productAdapter = ProductAdapter(productList){ productId ->

                // Navigation zur Produktdetailseite
                val action = CategoryFragmentDirections.actionCategoryFragmentToProductDetailFragment(productId)
                findNavController().navigate(action)
            }

            recyclerView.adapter = productAdapter
            toolbar.setNavigationIcon(R.drawable.ic_back)
            toolbar.title = "Products"
            isShowingProducts = true
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
        }
    }
}