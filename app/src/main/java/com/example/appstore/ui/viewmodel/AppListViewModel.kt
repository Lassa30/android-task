package com.example.appstore.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appstore.data.model.App
import com.example.appstore.data.repository.AppRepository

class AppListViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _apps = MutableLiveData<List<App>>()
    val apps: LiveData<List<App>> = _apps

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadApps()
    }

    fun loadApps() {
        _isLoading.value = true
        _error.value = null
        try {
            val appList = repository.getApps()
            _apps.value = appList
        } catch (e: Exception) {
            _error.value = e.message ?: "Unknown error"
        } finally {
            _isLoading.value = false
        }
    }

    fun searchApps(query: String): List<App> {
        val currentApps = _apps.value ?: emptyList()
        return if (query.isEmpty()) {
            currentApps
        } else {
            currentApps.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun filterByCategory(category: String): List<App> {
        val currentApps = _apps.value ?: emptyList()
        return if (category == "All") {
            currentApps
        } else {
            currentApps.filter { it.category == category }
        }
    }
}