package com.le_do.minishop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.databinding.FragmentProfileBinding
import com.le_do.minishop.utils.SessionManager
import com.le_do.minishop.R
import androidx.appcompat.app.AppCompatDelegate

// Profil-Seite der App mit verschiedenen Benutzeroptionen
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    // Layout des Fragments laden
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Aktionen der Buttons im Profil
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Öffnet die Account-Seite
        binding.btnAccount.setOnClickListener {
            findNavController().navigate(R.id.accountFragment)
        }

        // Öffnet die Bestellübersicht
        binding.btnMyOrders.setOnClickListener {
            findNavController().navigate(R.id.myOrdersFragment)
        }

        // Benutzer ausloggen und zur Login-Seite gehen
        binding.btnLogout.setOnClickListener {
            SessionManager(requireContext()).logout()
            findNavController().navigate(R.id.loginFragment)
        }

        // Dark Mode ein/aus schalten
        binding.btnDarkMode.setOnClickListener {
            toggleDarkMode()
        }
    }

    // Wechselt zwischen Light Mode und Dark Mode
    private fun toggleDarkMode() {
        val nightMode = AppCompatDelegate.getDefaultNightMode()

        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}