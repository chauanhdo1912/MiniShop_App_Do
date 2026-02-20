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
class ProductDetailFragment : Fragment() {

    private val cartViewModel: CartViewModel by activityViewModels ()

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ProductDetailFragmentArgs by navArgs()
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val productId = args.productId
        viewModel.fetchProduct(productId)

        viewModel.product.observe(viewLifecycleOwner) { product ->
            binding.tvTitle.text = product.title
            binding.tvPrice.text = "€${product.price}"
            binding.tvDescription.text = product.description
            binding.btnAddToCart.setOnClickListener {
                cartViewModel.addToCart(product)
            }

            Glide.with(this)
                .load(product.image)
                .into(binding.ivProductImage)

            binding.btnAddToCart.setOnClickListener {
                cartViewModel.addToCart(product)
                Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                val action = ProductDetailFragmentDirections.actionProductDetailFragmentToCartFragment()
                findNavController().navigate(action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

