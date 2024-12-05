package com.asyfdzaky.uas_pppb_fitnes_app.Admin

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan.Latihan
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiService
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ItemLatihanAdminBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

typealias OnClickLatihan = (Latihan) -> Unit

class AdminLatihanAdapter(
    private val listLatihan: ArrayList<Latihan>,
    private val client: ApiService,
    private val onClickLatihan: OnClickLatihan,
) : RecyclerView.Adapter<AdminLatihanAdapter.ItemLatihanViewHolder>() {
    inner class ItemLatihanViewHolder(private val binding: ItemLatihanAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Latihan) {
            with(binding) {

                tvWorkoutTitle.text = data.name

                itemView.setOnClickListener{
//                    val intent = Intent(itemView.context, AdminDetailActivity::class.java)
//                    intent.putExtra("id", data.id)
//                    intent.putExtra("name", data.name)
//                    intent.putExtra("jumlahLatihan", data.jumlahLatihan)
//                    intent.putExtra("JumlahRepetisi", data.jumlahRepetisi)
//                    data.details.forEachIndexed { index, detail ->
//                        intent.putExtra("details$index", detail)
//                    }
//                    itemView.context.startActivity(intent)
                    onClickLatihan(data)
                }

                btnEdit.setOnClickListener {
                    val intentEdit = Intent(itemView.context, AdminUpdateActivity::class.java)
                    intentEdit.putExtra("id", data.id)
                    intentEdit.putExtra("name", data.name)
                    intentEdit.putExtra("jumlahLatihan", data.jumlahLatihan)
                    intentEdit.putExtra("jumlahRepetisi", data.jumlahRepetisi)
                    intentEdit.putExtra("details1", data.details[0])
                    intentEdit.putExtra("details2", data.details[1])
                    intentEdit.putExtra("details3", data.details[2])
                    intentEdit.putExtra("details4", data.details[3])
                    intentEdit.putExtra("details5", data.details[4])
                    itemView.context.startActivity(intentEdit)
                }
                btnDelete.setOnClickListener {
                    val response = data.id?.let { it1 -> client.deleteLatihan(it1) }
                    response?.enqueue(object : Callback<Latihan> {
                        override fun onResponse(
                            call: Call<Latihan>,
                            response: Response<Latihan>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    itemView.context,
                                    "data latihan ${data.name} berhasil dihapus",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val position = adapterPosition
                                if (position != RecyclerView.NO_POSITION) {
                                    removeItem(position)
                                }
                            } else {
                                Log.e("API Error", "Response not successful or body is null")
                            }
                        }

                        override fun onFailure(call: Call<Latihan>, t: Throwable) {
                            Toast.makeText(itemView.context, "koneksi eror", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLatihanViewHolder {

        val binding =
            ItemLatihanAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemLatihanViewHolder(binding)
    }

    override fun getItemCount(): Int = listLatihan.size


    override fun onBindViewHolder(holder: ItemLatihanViewHolder, position: Int) {
        holder.bind(listLatihan[position])
    }

    fun updateData(newList: List<Latihan>) {
        listLatihan.clear()
        listLatihan.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        listLatihan.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, listLatihan.size)
    }

}