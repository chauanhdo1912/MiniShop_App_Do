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
import com.le_do.minishop.data.local.OrderRepository
import com.le_do.minishop.viewmodel.OrderViewModel
import com.le_do.minishop.viewmodel.OrderViewModelFactory
import com.le_do.minishop.data.local.AppDatabase
import androidx.lifecycle.ViewModelProvider
import com.le_do.minishop.utils.SessionManager

// Fragment zeigt den Warenkorb und ermöglicht Bestellung
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderViewModel: OrderViewModel

    // Gemeinsames ViewModel für den Warenkorb
    private val cartViewModel: CartViewModel by activityViewModels()

    // Layout des Fragments laden
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Warenkorb anzeigen und Bestellung vorbereiten
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel für Bestellungen erstellen
        val db = AppDatabase.getInstance(requireContext())
        val orderRepository = OrderRepository(db.OrderDao())
        val factory = OrderViewModelFactory(orderRepository)
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]

        // Beobachtet Änderungen im Warenkorb
        cartViewModel.cartItems.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.tvEmptyCart.visibility = View.VISIBLE
                binding.rvCart.visibility = View.GONE
            } else {
                binding.tvEmptyCart.visibility = View.GONE
                binding.rvCart.visibility = View.VISIBLE
            }

            // Produkte im Warenkorb anzeigen
            binding.rvCart.adapter = CartAdapter(
                items = list,
                onIncrease = { cartViewModel.increase(it) },
                onDecrease = { cartViewModel.decrease(it) },
                onRemove = { cartViewModel.removeFromCart(it.product.id) }
            )
        }

        // Gesamtpreis berechnen und anzeigen
        cartViewModel.totalPrice.observe(viewLifecycleOwner){ total ->
            binding.tvTotalPrice.text = "Total Price: €${String.format("%.2f",total)}"
        }

        // Toolbar mit Titel und Back-Button
        binding.toolbarCart.title = "Cart"
        binding.toolbarCart.setNavigationIcon(R.drawable.ic_back)
        binding.toolbarCart.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // RecyclerView Layout
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())

        // Checkout Button
        binding.btnCheckout.setOnClickListener {

            val cartList = cartViewModel.cartItems.value ?: emptyList()

            // Prüfen ob Warenkorb leer ist
            if (cartList.isEmpty()) {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Login Status prüfen
            val session = SessionManager(requireContext())

            if (!session.isLoggedIn()) {
                findNavController().navigate(R.id.loginFragment)
                return@setOnClickListener
            }

            val userEmail = session.getEmail() ?: ""

            // Bestellung erstellen
            orderViewModel.createOrder(userEmail, cartList)

            // Warenkorb leeren
            cartViewModel.clearCart()

            Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    // Binding freigeben
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}