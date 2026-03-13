package com.le_do.minishop.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.le_do.minishop.data.local.AppDatabase
import com.le_do.minishop.data.local.UserRepository
import com.le_do.minishop.databinding.FragmentEditProfileBinding
import com.le_do.minishop.utils.SessionManager
import com.le_do.minishop.viewmodel.UserViewModel
import com.le_do.minishop.viewmodel.UserViewModelFactory
import android.content.Intent
import java.io.File

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    private var savedImagePath: String? = null

    private fun saveImageToInternalStorage(uri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val fileName = "avatar_${System.currentTimeMillis()}.jpg"
        val file = File(requireContext().filesDir, fileName)

        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {

                val savedPath = saveImageToInternalStorage(it)

                savedImagePath = savedPath

                binding.ivAvatar.setImageURI(Uri.fromFile(File(savedPath)))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        val repository = UserRepository(db.userDao())
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        val email = SessionManager(requireContext()).getEmail() ?: return
        viewModel.getUserByEmail(email)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {

                binding.etAddress.setText(it.address ?: "")

                if (!it.avatarUri.isNullOrEmpty()) {
                    binding.ivAvatar.setImageURI(Uri.parse(it.avatarUri))
                }
            }
        }

        binding.ivAvatar.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnSave.setOnClickListener {

            val address = binding.etAddress.text.toString()

            viewModel.user.value?.let { currentUser ->

                val updatedUser = currentUser.copy(
                    address = address,
                    avatarUri = savedImagePath ?: currentUser.avatarUri
                )

                viewModel.updateUser(updatedUser)

                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}