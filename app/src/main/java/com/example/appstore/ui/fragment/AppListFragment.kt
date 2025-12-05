package com.example.appstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstore.R
import com.example.appstore.databinding.FragmentAppListBinding
import com.example.appstore.ui.adapter.AppAdapter
import com.example.appstore.ui.viewmodel.AppListViewModel

class AppListFragment : Fragment() {

    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppListViewModel by viewModels()
    private lateinit var adapter: AppAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        setupSwipeRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = AppAdapter { app ->
            val bundle = Bundle().apply {
                putString("appId", app.id)
            }
            findNavController().navigate(R.id.appDetailFragment, bundle)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { text ->
            val query = text.toString()
            val filteredApps = viewModel.searchApps(query)
            adapter.submitList(filteredApps)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadApps()
        }
    }

    private fun observeViewModel() {
        viewModel.apps.observe(viewLifecycleOwner) { apps ->
            adapter.submitList(apps)
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                // Show error message
                binding.errorText.text = it
                binding.errorText.visibility = View.VISIBLE
                binding.retryButton.visibility = View.VISIBLE
            } ?: run {
                binding.errorText.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadApps()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}