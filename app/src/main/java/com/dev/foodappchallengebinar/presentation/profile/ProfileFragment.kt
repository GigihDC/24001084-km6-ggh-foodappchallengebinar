package com.dev.foodappchallengebinar.presentation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.dev.foodappchallengebinar.R
import com.dev.foodappchallengebinar.databinding.FragmentProfileBinding
import com.dev.foodappchallengebinar.presentation.login.LoginActivity
import com.dev.foodappchallengebinar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModel()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                changePhotoProfile(uri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        setClickListener()
        showUserData()
        observeData()
    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.etEmail.isEnabled = false
    }

    private fun changePhotoProfile(uri: Uri) {
        profileViewModel.updateProfilePicture(uri)
    }

    private fun showUserData() {
        profileViewModel.getCurrentUser()?.let {
            binding.layoutForm.etName.setText(it.fullName)
            binding.layoutForm.etEmail.setText(it.email)
        }
    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            if (checkNameValidation()) {
                changeProfileData()
            }
        }
        binding.ivEditPhoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnRequestChangePassword.setOnClickListener {
            requestChangePassword()
        }
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun requestChangePassword() {
        profileViewModel.createChangePwdRequest()
        val dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.text_request_success, profileViewModel.getCurrentUser()?.email))
                .setPositiveButton(
                    getString(R.string.text_okay),
                ) { dialog, id ->
                    dialog.dismiss()
                }.create()
        dialog.show()
    }

    private fun changeProfileData() {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        profileViewModel.updateFullName(fullName)
    }

    private fun checkNameValidation(): Boolean {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        return if (fullName.isEmpty()) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.text_alert))
            .setMessage(getString(R.string.text_want_logout))
            .setPositiveButton(getString(R.string.text_yes)) { dialog, id ->
                navigateToLogin()
            }
            .setNegativeButton(getString(R.string.text_no)) { dialog, id ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLogin() {
        profileViewModel.doLogout()
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun observeData() {
        profileViewModel.changePhotoResult.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_change_photo_profile_success),
                    Toast.LENGTH_SHORT,
                ).show()
                showUserData()
            }, doOnError = {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_change_photo_profile_failed),
                    Toast.LENGTH_SHORT,
                ).show()
                showUserData()
            })
        }
        profileViewModel.changeProfileResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnEdit.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.change_profile_data_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnEdit.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_change_profile_data_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnEdit.isVisible = true
                },
            )
        }
    }
}
