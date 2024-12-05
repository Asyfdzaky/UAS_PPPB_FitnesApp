package com.asyfdzaky.uas_pppb_fitnes_app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan.Latihan
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiService
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ItemLatihanBinding

typealias onClickLatihan = (Latihan) -> Unit

class HomeLatihanAdapter(
    private val listLatihan: ArrayList<Latihan>,
    private val client: ApiService,
    private val onClickLatihan: onClickLatihan,
) : RecyclerView.Adapter<HomeLatihanAdapter.ItemLatihanViewHolder>() {
    inner class ItemLatihanViewHolder(private val binding: ItemLatihanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Latihan) {
            with(binding) {
                tvWorkoutTitle.text = data.name
                tvWorkoutCount.text = data.jumlahLatihan
                tvExercise1Name.text = data.details[0]
                tvExercise2Name.text = data.details[1]
                tvExercise3Name.text = data.details[2]
                tvExercise1Reps.text  = data.jumlahRepetisi.substring(0,5)
                tvExercise2Reps.text  = data.jumlahRepetisi.substring(0,5)
                tvExercise3Reps.text  = data.jumlahRepetisi.substring(0,5)


                itemView.setOnClickListener {
                    onClickLatihan(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLatihanViewHolder {
        val binding = ItemLatihanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemLatihanViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listLatihan.size
    }

    override fun onBindViewHolder(holder: ItemLatihanViewHolder, position: Int) {
        holder.bind(listLatihan[position])
    }

}