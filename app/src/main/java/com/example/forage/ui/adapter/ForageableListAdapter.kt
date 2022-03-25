package com.example.forage.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forage.databinding.ListItemForageableBinding
import com.example.forage.model.Forageable

class ForageableListAdapter(private val clickListener: (Forageable) -> Unit) :
    ListAdapter<Forageable, ForageableListAdapter.ForageableViewHolder>(DiffCallback) {

    class ForageableViewHolder(private val binding: ListItemForageableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(forageable: Forageable) {
            binding.forageable = forageable
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForageableViewHolder {
        return ForageableViewHolder(
            ListItemForageableBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ForageableViewHolder, position: Int) {
        val forageable = getItem(position)
        holder.apply {
            bind(forageable)
            itemView.setOnClickListener { clickListener(forageable) }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Forageable>() {
        override fun areItemsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Forageable, newItem: Forageable): Boolean {
            return oldItem == newItem
        }

    }
}