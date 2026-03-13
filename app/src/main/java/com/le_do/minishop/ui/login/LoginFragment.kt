package com.le_do.minishop.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.R
import com.le_do.minishop.databinding.FragmentLoginBinding
import com.le_do.minishop.data.local.AppDatabase
import com.le_do.minishop.utils.SessionManager
import com.le_do.minishop.viewmodel.UserViewModel
import com.le_do.minishop.viewmodel.UserViewModelFactory
import com.le_do.minishop.data.local.UserRepository

// Login-Seite der App
class LoginFragment : Fragment() {

    // Binding für die UI
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // Speichert den Login-Zustand
    private lateinit var session: SessionManager

    // ViewModel mit Datenbank und Repository
    private val viewModel: UserViewModel by viewModels {
        val db = AppDatabase.getInstance(requireContext())
        val userRepository = UserRepository(db.userDao())
        UserViewModelFactory(userRepository)
    }

    // Layout wird hier geladen
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Hier passiert die Logik vom Login
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        // Wenn Login erfolgreich
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()

                // Login speichern
                session.saveLogin(user.email)

                // Zur Home-Seite wechseln
                findNavController().navigate(
                    R.id.homeFragment,
                    null,
                    androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()
                )
            }
        }

        // Fehler beim Login anzeigen
        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // Login Button
        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Prüfen ob Felder leer sind
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Login starten
            viewModel.login(email, password)
        }

        // Link zur Register-Seite
        binding.tvRegisterLink.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    // Binding löschen wenn Fragment zerstört wird
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}