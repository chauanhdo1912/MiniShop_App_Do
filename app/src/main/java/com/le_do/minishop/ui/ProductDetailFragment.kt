package com.le_do.minishop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.le_do.minishop.R
import com.le_do.minishop.databinding.FragmentProductDetailBinding
import com.le_do.minishop.viewmodel.CartViewModel
import android.widget.Toast

// Fragment zeigt die Details eines Produkts
class ProductDetailFragment : Fragment() {

    // Gemeinsames ViewModel für den Warenkorb
    private val cartViewModel: CartViewModel by activityViewModels ()

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    // Produkt-ID wird aus der Navigation übergeben
    private val args: ProductDetailFragmentArgs by navArgs()

    // ViewModel für Produktdaten
    private val viewModel: ProductDetailViewModel by viewModels()

    // Layout des Fragments laden
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Produktdaten anzeigen und Aktionen definieren
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar mit Back-Button
        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Produkt über ID laden
        val productId = args.productId
        viewModel.fetchProduct(productId)

        // Produktdaten beobachten und UI aktualisieren
        viewModel.product.observe(viewLifecycleOwner) { product ->
            binding.tvTitle.text = product.title
            binding.tvPrice.text = "€${product.price}"
            binding.tvDescription.text = product.description

            // Produktbild laden
            Glide.with(this)
                .load(product.image)
                .into(binding.ivProductImage)

            // Produkt zum Warenkorb hinzufügen
            binding.btnAddToCart.setOnClickListener {
                cartViewModel.addToCart(product)
                Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()

                // Weiter zum Warenkorb
                findNavController().navigate(R.id.cartFragment)
            }

        }
    }

    // Binding freigeben um Memory-Leaks zu vermeiden
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}