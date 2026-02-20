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

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var session: SessionManager
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        val db = AppDatabase.getInstance(requireContext())
        val userRepository = UserRepository(db.userDao())
        viewModel = ProfileViewModel(userRepository)

        val email = session.getEmail() ?: return
        viewModel.loadUser(email)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvName.text = user.name
            binding.tvEmail.text = user.email
        }

        binding.btnLogout.setOnClickListener {
            session.logout()
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
