package com.tp.testrap.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tp.testrap.core.BaseViewHolder
import com.tp.testrap.databinding.ItemStringGenericSelectionBinding

class GenericListAdapter(private val callback: CallbackItem) :
    ListAdapter<String, BaseViewHolder<*>>(DiffCallback()) {

    private inner class VerticalViewHolder(val binding: ItemStringGenericSelectionBinding) :
        BaseViewHolder<String>(binding.root) {
        override fun bind(item: String) {
            with(binding) {
                itemString = item
                executePendingBindings()
                itemSelectText.setOnClickListener {
                    callback.onItemSelected(adapterPosition)
                }
            }
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as VerticalViewHolder).bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemStringGenericSelectionBinding.inflate(layoutInflater, parent, false)
        return VerticalViewHolder(binding)

    }
}

class DiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
