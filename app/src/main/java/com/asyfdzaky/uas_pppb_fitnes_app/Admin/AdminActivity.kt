package com.asyfdzaky.uas_pppb_fitnes_app.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.asyfdzaky.shareprefence.PrefManager
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan.Latihan
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiClient
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityAdminBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var adapter: AdminLatihanAdapter
    private val latihanList = ArrayList<Latihan>()
    private lateinit var prefmanager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        prefmanager = PrefManager.getInstance(this)
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchLatihanList()
        binding.progressBar.visibility = android.view.View.VISIBLE
        // Tombol untuk Create Activity
        binding.btnCreate.setOnClickListener {
            startActivity(
                Intent(
                    this@AdminActivity,
                    AdminCreateActivity::class.java
                )
            )
        }
        binding.btnLogout.setOnClickListener{
            prefmanager.clear()
            finish()

        }
    }

    private fun setupRecyclerView() {
        adapter = AdminLatihanAdapter(latihanList, ApiClient.getInstance()) { data ->
            val intent = Intent(this, AdminDetailActivity::class.java).apply {
                putExtra("id", data.id)
                putExtra("name", data.name)
                putExtra("jumlahLatihan", data.jumlahLatihan)
                putExtra("jumlahRepetisi", data.jumlahRepetisi)
                // Validasi jumlah elemen pada details untuk menghindari IndexOutOfBoundsException
                putExtra("details1", data.details[0])
                putExtra("details2", data.details[1])
                putExtra("details3", data.details[2])
                putExtra("details4", data.details[3])
                putExtra("details5", data.details[4])
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun fetchLatihanList() {
        val client = ApiClient.getInstance()
        val response = client.getAllLatihan()

        response.enqueue(object : Callback<List<Latihan>> {
            override fun onResponse(call: Call<List<Latihan>>, response: Response<List<Latihan>>) {
                if (response.isSuccessful && response.body() != null) {
                    // Tambahkan data ke dalam list dan perbarui adapter
                    binding.progressBar.visibility = android.view.View.GONE
                    latihanList.clear()
                    latihanList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@AdminActivity,
                        "Gagal memuat data latihan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Latihan>>, t: Throwable) {
                Toast.makeText(
                    this@AdminActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
