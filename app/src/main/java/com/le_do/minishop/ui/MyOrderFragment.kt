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

// Fragment zeigt die Bestellungen des aktuellen Benutzers
class MyOrdersFragment : Fragment() {

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: OrderViewModel

    // Layout des Fragments laden
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Bestellungen laden und anzeigen
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar mit Titel und Back-Button
        binding.toolbarMyOrders.title = "My Orders"
        binding.toolbarMyOrders.setNavigationIcon(R.drawable.ic_back)
        binding.toolbarMyOrders.setNavigationOnClickListener { findNavController().navigateUp() }

        // ViewModel mit Repository und Datenbank erstellen
        val db = AppDatabase.getInstance(requireContext())
        val repository = OrderRepository(db.OrderDao())
        val factory = OrderViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]

        // RecyclerView für die Bestellliste
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())

        // Email des eingeloggten Users holen
        val email = SessionManager(requireContext()).getEmail()

        // Bestellungen des Users laden
        viewModel.loadOrders(email)

        // Bestellungen im RecyclerView anzeigen
        viewModel.orders.observe(viewLifecycleOwner) { list ->
            binding.rvOrders.adapter = MyOrdersAdapter(list)
        }
    }

    // Binding freigeben
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}