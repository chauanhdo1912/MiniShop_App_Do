package com.le_do.minishop.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.util.Patterns
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.R
import com.le_do.minishop.databinding.FragmentRegisterBinding
import com.le_do.minishop.data.local.AppDatabase
import com.le_do.minishop.data.local.entity.User
import com.le_do.minishop.viewmodel.UserViewModel
import com.le_do.minishop.viewmodel.UserViewModelFactory
import com.le_do.minishop.data.local.UserRepository

// Registrierungsseite der App
class RegisterFragment : Fragment() {

    // Binding für Zugriff auf die UI
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Logik für Registrierung
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Wenn Registrierung erfolgreich
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Register successful", Toast.LENGTH_SHORT).show()

                // Zur Login-Seite wechseln
                findNavController().navigate(R.id.loginFragment)
            }
        }

        // Fehlermeldung anzeigen
        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        // Register Button
        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Prüfen ob Felder leer sind
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Email Format prüfen
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Invalid email"
                return@setOnClickListener
            }

            // Passwort Länge prüfen
            if (password.length < 6) {
                binding.etPassword.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            // neuen User erstellen
            val createAt = System.currentTimeMillis()
            val user = User(email, name, password, createAt)

            // Registrierung starten
            viewModel.register(user)
        }

        // Link zurück zur Login-Seite
        binding.tvLoginLink.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    // Binding löschen um Memory-Leaks zu vermeiden
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}