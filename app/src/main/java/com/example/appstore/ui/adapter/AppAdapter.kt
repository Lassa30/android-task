package com.example.appstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appstore.data.model.App
import com.example.appstore.databinding.ItemAppBinding

class AppAdapter(
    private val onAppClick: (App) -> Unit
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    private var apps: List<App> = emptyList()

    fun submitList(newApps: List<App>) {
        apps = newApps
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(apps[position])
    }

    override fun getItemCount(): Int = apps.size

    inner class AppViewHolder(private val binding: ItemAppBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onAppClick(apps[position])
                }
            }
        }

        fun bind(app: App) {
            binding.appName.text = app.name
            binding.appDescription.text = app.description
            binding.appRating.text = app.rating.toString()
            binding.appCategory.text = app.category

            Glide.with(binding.appIcon.context)
                .load(app.iconUrl)
                .into(binding.appIcon)
        }
    }
}