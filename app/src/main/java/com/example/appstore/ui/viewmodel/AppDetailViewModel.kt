package com.example.appstore.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appstore.data.model.App
import com.example.appstore.data.repository.AppRepository

class AppDetailViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _app = MutableLiveData<App?>()
    val app: LiveData<App?> = _app

    private val _isInstalling = MutableLiveData<Boolean>()
    val isInstalling: LiveData<Boolean> = _isInstalling

    private val _installError = MutableLiveData<String?>()
    val installError: LiveData<String?> = _installError

    fun loadApp(appId: String) {
        val appData = repository.getAppById(appId)
        _app.value = appData
    }

    fun installApp() {
        _isInstalling.value = true
        _installError.value = null
        // Simulate installation
        // In real app, download and install APK
        _isInstalling.value = false
        _app.value = _app.value?.copy(isInstalled = true)
    }

    fun uninstallApp() {
        // Simulate uninstallation
        _app.value = _app.value?.copy(isInstalled = false)
    }
}