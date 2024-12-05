package com.asyfdzaky.uas_pppb_fitnes_app.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan.Latihan
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiClient
import com.asyfdzaky.uas_pppb_fitnes_app.R
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityAdminCreateBinding
import retrofit2.Call
import retrofit2.Callback

class AdminCreateActivity : AppCompatActivity() {
        private lateinit var binding: ActivityAdminCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnSubmitLatihan.setOnClickListener{
                val name = etWorkoutName.text.toString()
                val jumlahWo = etJumlahWorkout.text.toString()
                val jumlahRep = etJumlahRepetisi.text.toString()
                val detailWo = etDetailWorkout1.text.toString()
                val detailWo2 = etDetailWorkout2.text.toString()
                val detailWo3 = etDetailWorkout3.text.toString()
                val detailWo4 = etDetailWorkout4.text.toString()
                val detailWo5 = etDetailWorkout5.text.toString()

                val latihan = Latihan(
                    name = name,
                    jumlahLatihan = jumlahWo,
                    jumlahRepetisi = jumlahRep,
                    details = listOf(
                        detailWo,
                        detailWo2,
                        detailWo3,
                        detailWo4,
                        detailWo5
                    ).filter { it.isNotEmpty() }
                )
                val apiService = ApiClient.getInstance()
                val response = apiService.createLatihan(latihan)
                response.enqueue(object : Callback<Latihan> {
                    override fun onResponse(call: Call<Latihan>, response: retrofit2.Response<Latihan>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@AdminCreateActivity, "Latihan berhasil dibuat", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@AdminCreateActivity, AdminActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@AdminCreateActivity, "Gagal membuat workout", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Latihan>, t: Throwable) {
                        Toast.makeText(this@AdminCreateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }
    }
}