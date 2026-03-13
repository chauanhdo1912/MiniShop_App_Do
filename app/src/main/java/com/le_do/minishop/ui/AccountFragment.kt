package com.le_do.minishop.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.le_do.minishop.databinding.FragmentAccountBinding
import com.le_do.minishop.data.local.AppDatabase
import com.le_do.minishop.data.local.UserRepository
import com.le_do.minishop.viewmodel.UserViewModel
import com.le_do.minishop.viewmodel.UserViewModelFactory
import com.le_do.minishop.utils.SessionManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.R
import android.net.Uri
import java.io.File
import android.util.Log
import com.bumptech.glide.Glide
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val db = AppDatabase.getInstance(requireContext())
        val repository = UserRepository(db.userDao())
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[UserViewModel::class.java]

        val email = SessionManager(requireContext()).getEmail() ?: return

        viewModel.getUserByEmail(email)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.tvEmail.text = it.email
                binding.tvName.text = it.name

                binding.tvAddress.text = it.address ?: "No address"

                if (!it.avatarUri.isNullOrEmpty()) {

                    val file = File(it.avatarUri!!)
                    Log.d("PATH_DEBUG", "Saved path: ${it.avatarUri}")
                    Log.d("FILE_CHECK", "Exists: ${file.exists()} Size: ${file.length()}")

                    if (file.exists()) {
                        Glide.with(requireContext())
                            .load(file)
                            .override(300, 300)
                            .centerCrop()
                            .placeholder(R.drawable.ic_account)
                            .error(R.drawable.ic_account)
                            .into(binding.ivAvatar)
                    } else {
                        binding.ivAvatar.setImageResource(R.drawable.ic_account)
                    }

                } else {
                    binding.ivAvatar.setImageResource(R.drawable.ic_account)
                }

                val date = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                binding.tvMemberSince.text =
                    date.format(Date(user.createdAt))

            }
        }
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(
                R.id.action_accountFragment_to_editProfileFragment
            )
        }
    }
    override fun onResume() {
        super.onResume()

        val email = SessionManager(requireContext()).getEmail() ?: return
        viewModel.getUserByEmail(email)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}