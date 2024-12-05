package com.asyfdzaky.uas_pppb_fitnes_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan.Latihan
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Room.LatihanTable
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ItemBookmarkBinding



class LatihanFavoriteAdapater(
    private val Context: Context,
    private val deleteAction: (LatihanTable) -> Unit,
    private val LatihanDetail: (LatihanTable) -> Unit
) : ListAdapter<LatihanTable, LatihanFavoriteAdapater.ItemLatihanViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<LatihanTable>() {
        override fun areItemsTheSame(oldItem: LatihanTable, newItem: LatihanTable): Boolean {
            return oldItem.id == newItem.id // Check if the IDs are the same
        }

        override fun areContentsTheSame(oldItem: LatihanTable, newItem: LatihanTable): Boolean {
            return oldItem == newItem // Check if the content is the same
        }
    }

    inner class ItemLatihanViewHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: LatihanTable) {
            with(binding) {
                tvWorkoutTitle.text = data.name
                tvWorkoutCount.text = data.jumlahLatihan

                // Handle delete button click
                delete.setOnClickListener {
                    deleteAction(data)
                }
                itemView.setOnClickListener{
                    LatihanDetail(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLatihanViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemLatihanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemLatihanViewHolder, position: Int) {
        holder.bind(getItem(position)) // Only pass the data to bind
    }
}
