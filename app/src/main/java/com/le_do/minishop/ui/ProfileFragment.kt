package com.le_do.minishop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.databinding.FragmentProfileBinding
import com.le_do.minishop.utils.SessionManager
import com.le_do.minishop.viewmodel.ProfileViewModel
import com.le_do.minishop.R
import com.le_do.minishop.data.local.AppDatabase
import com.le_do.minishop.data.local.UserRepository
import androidx.appcompat.app.AppCompatDelegate

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnAccount.setOnClickListener {
            findNavController().navigate(R.id.accountFragment)
        }

        binding.btnMyOrders.setOnClickListener {
            findNavController().navigate(R.id.myOrdersFragment)
        }

        binding.btnLogout.setOnClickListener {
            SessionManager(requireContext()).logout()
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btnDarkMode.setOnClickListener {
            toggleDarkMode()
        }
    }

    private fun toggleDarkMode() {
        val nightMode = AppCompatDelegate.getDefaultNightMode()

        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}