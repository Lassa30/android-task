package com.example.appstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.appstore.databinding.FragmentAppDetailBinding
import com.example.appstore.ui.viewmodel.AppDetailViewModel

class AppDetailFragment : Fragment() {

    private var _binding: FragmentAppDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appId = arguments?.getString("appId") ?: return
        viewModel.loadApp(appId)

        observeViewModel()
        setupButtons()
    }

    private fun observeViewModel() {
        viewModel.app.observe(viewLifecycleOwner) { app ->
            app?.let {
                binding.appName.text = it.name
                binding.appDescription.text = it.description
                binding.appRating.text = it.rating.toString()
                binding.appReviews.text = "${it.reviews} reviews"
                binding.appVersion.text = it.version
                binding.appSize.text = "${it.size / (1024 * 1024)} MB"

                Glide.with(requireContext())
                    .load(it.iconUrl)
                    .into(binding.appIcon)

                // Load screenshots (simplified, load first one)
                if (it.screenshots.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load(it.screenshots[0])
                        .into(binding.screenshotImage)
                }

                updateInstallButton(it.isInstalled)
            }
        }

        viewModel.isInstalling.observe(viewLifecycleOwner) { isInstalling ->
            binding.installButton.isEnabled = !isInstalling
            binding.installButton.text = if (isInstalling) "Installing..." else if (viewModel.app.value?.isInstalled == true) "Uninstall" else "Install"
        }

        viewModel.installError.observe(viewLifecycleOwner) { error ->
            error?.let {
                // Show error
            }
        }
    }

    private fun setupButtons() {
        binding.installButton.setOnClickListener {
            if (viewModel.app.value?.isInstalled == true) {
                viewModel.uninstallApp(requireContext())
            } else {
                viewModel.installApp(requireContext())
            }
        }
    }

    private fun updateInstallButton(isInstalled: Boolean) {
        binding.installButton.text = if (isInstalled) "Uninstall" else "Install"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}