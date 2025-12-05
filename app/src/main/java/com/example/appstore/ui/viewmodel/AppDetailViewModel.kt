package com.example.appstore.ui.viewmodel

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appstore.data.model.App
import com.example.appstore.data.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AppDetailViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _app = MutableLiveData<App?>()
    val app: LiveData<App?> = _app

    private val _isInstalling = MutableLiveData<Boolean>()
    val isInstalling: LiveData<Boolean> = _isInstalling

    private val _installError = MutableLiveData<String?>()
    val installError: LiveData<String?> = _installError

    private val _downloadProgress = MutableLiveData<Int>()
    val downloadProgress: LiveData<Int> = _downloadProgress

    fun loadApp(appId: String) {
        val appData = repository.getAppById(appId)
        _app.value = appData
    }

    fun installApp(context: Context) {
        val app = _app.value ?: return
        _isInstalling.value = true
        _installError.value = null

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Download APK
                downloadApk(context, app.apkUrl) { success, file ->
                    if (success && file != null) {
                        // Install APK
                        installApk(context, file)
                    } else {
                        _installError.postValue("Download failed")
                        _isInstalling.postValue(false)
                    }
                }
            } catch (e: Exception) {
                _installError.postValue(e.message)
                _isInstalling.postValue(false)
            }
        }
    }

    private fun downloadApk(context: Context, url: String, callback: (Boolean, File?) -> Unit) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading APK")
            .setDescription("Downloading ${app.value?.name}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        // Monitor download progress (simplified)
        // In real app, use BroadcastReceiver or WorkManager
        callback(true, File(context.cacheDir, "temp.apk")) // Mock file
    }

    private fun installApk(context: Context, apkFile: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val packageInstaller = context.packageManager.packageInstaller
            val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
            params.setAppPackageName(app.value?.packageName)

            val sessionId = packageInstaller.createSession(params)
            val session = packageInstaller.openSession(sessionId)

            apkFile.inputStream().use { input ->
                session.openWrite("package", 0, apkFile.length()).use { output ->
                    input.copyTo(output)
                    session.fsync(output)
                }
            }

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val statusReceiver = pendingIntent.intentSender

            session.commit(statusReceiver)
            _isInstalling.postValue(false)
            _app.postValue(_app.value?.copy(isInstalled = true))
        } else {
            // Fallback for older versions
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", apkFile)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
            _isInstalling.postValue(false)
        }
    }

    fun uninstallApp(context: Context) {
        val app = _app.value ?: return
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:${app.packageName}")
        context.startActivity(intent)
        _app.value = _app.value?.copy(isInstalled = false)
    }
}