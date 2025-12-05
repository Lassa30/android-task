package com.example.appstore.data.repository

import com.example.appstore.data.model.App

class AppRepository {

    // Mock data for MVP
    fun getApps(): List<App> {
        return listOf(
            App(
                id = "1",
                name = "Sample App 1",
                description = "This is a sample app description.",
                iconUrl = "https://via.placeholder.com/150",
                screenshots = listOf("https://via.placeholder.com/300x500"),
                apkUrl = "https://example.com/app1.apk",
                packageName = "com.example.app1",
                version = "1.0.0",
                size = 1024 * 1024 * 10, // 10MB
                rating = 4.5f,
                reviews = 100,
                category = "Tools"
            ),
            App(
                id = "2",
                name = "Sample App 2",
                description = "Another sample app.",
                iconUrl = "https://via.placeholder.com/150",
                screenshots = listOf("https://via.placeholder.com/300x500"),
                apkUrl = "https://example.com/app2.apk",
                packageName = "com.example.app2",
                version = "2.0.0",
                size = 1024 * 1024 * 15,
                rating = 4.0f,
                reviews = 50,
                category = "Games"
            )
            // Add more mock apps as needed
        )
    }

    fun getAppById(id: String): App? {
        return getApps().find { it.id == id }
    }
}