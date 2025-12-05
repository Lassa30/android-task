package com.example.appstore.data.model

data class App(
    val id: String,
    val name: String,
    val description: String,
    val iconUrl: String,
    val screenshots: List<String>,
    val apkUrl: String,
    val packageName: String,
    val version: String,
    val size: Long,
    val rating: Float,
    val reviews: Int,
    val category: String,
    var isInstalled: Boolean = false
)