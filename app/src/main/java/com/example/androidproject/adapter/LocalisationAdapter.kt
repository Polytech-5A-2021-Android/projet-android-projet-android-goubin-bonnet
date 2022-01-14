package com.example.androidproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.ItemViewBinding
import com.example.androidproject.model.Localisation

class LocalisationAdapter(val clickListener : LocalisationListener) : ListAdapter<Localisation, LocalisationAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
        holder.bind(getItem(position)!!, clickListener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LocalisationAdapter.ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Localisation, clickListener : LocalisationListener) {
            binding.localisation = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<Localisation>() {
        override fun areItemsTheSame(oldItem: Localisation, newItem: Localisation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Localisation, newItem: Localisation): Boolean {
            return oldItem == newItem
        }
    }
}