package com.le_do.minishop.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.le_do.minishop.R
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.databinding.FragmentRegisterBinding
import com.le_do.minishop.data.local.AppDatabase
import com.le_do.minishop.data.local.entity.User
import com.le_do.minishop.viewmodel.UserViewModel
import com.le_do.minishop.viewmodel.UserViewModelFactory
import com.le_do.minishop.data.local.UserRepository


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels {
        val db = AppDatabase.getInstance(requireContext())
        val userRepository = UserRepository(db.userDao())
        UserViewModelFactory(userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val user = User(email, name, password)
            viewModel.register(user)

            viewModel.user.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), "Register successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.loginFragment)
                }
            }

            viewModel.error.observe(viewLifecycleOwner) { err ->
                err?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            }
        }

        binding.tvLoginLink.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
