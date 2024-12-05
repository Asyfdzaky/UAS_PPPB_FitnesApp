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
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityAdminUpdateBinding
import retrofit2.Call
import retrofit2.Callback

class AdminUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ApiClient.getInstance()
        with(binding) {
            val id = intent.getStringExtra("id")
            val name = intent.getStringExtra("name")
            val jumlahWo = intent.getStringExtra("jumlahLatihan")
            val jumlahRep = intent.getStringExtra("jumlahRepetisi")
            val detailWo = intent.getStringExtra("details1")
            val detailWo2 = intent.getStringExtra("details2")
            val detailWo3 = intent.getStringExtra("details3")
            val detailWo4 = intent.getStringExtra("details4")
            val detailWo5 = intent.getStringExtra("details5")

            etWorkoutName.setText(name)
            etJumlahWorkout.setText(jumlahWo)
            etJumlahRepetisi.setText(jumlahRep)
            etDetailWorkout1.setText(detailWo)
            etDetailWorkout2.setText(detailWo2)
            etDetailWorkout3.setText(detailWo3)
            etDetailWorkout4.setText(detailWo4)
            etDetailWorkout5.setText(detailWo5)


            btnUpdateLatihan.setOnClickListener {

                val updatedName = etWorkoutName.text.toString()
                val updatedJumlahWo = etJumlahWorkout.text.toString()
                val updatedJumlahRep = etJumlahRepetisi.text.toString()
                val updatedDetails = listOf(
                    etDetailWorkout1.text.toString(),
                    etDetailWorkout2.text.toString(),
                    etDetailWorkout3.text.toString(),
                    etDetailWorkout4.text.toString(),
                    etDetailWorkout5.text.toString()
                ).filter { it.isNotEmpty() }
                if (validateForm(updatedName, updatedJumlahWo, updatedJumlahRep, updatedDetails)) {
                    val latihan = Latihan(
                        id = id,
                        name = updatedName,
                        jumlahLatihan = updatedJumlahWo,
                        jumlahRepetisi = updatedJumlahRep,
                        details = updatedDetails
                    )
                    val response = client.updateLatihan(id.toString(),latihan)
                    response.enqueue(object : Callback<Latihan> {
                        override fun onResponse(
                            call: Call<Latihan>,
                            response: retrofit2.Response<Latihan>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@AdminUpdateActivity,
                                    "Latihan berhasil diupdate",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@AdminUpdateActivity, AdminActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@AdminUpdateActivity,
                                    "Gagal update workout",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<Latihan>, t: Throwable) {
                            Toast.makeText(
                                this@AdminUpdateActivity,
                                "Error: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }


            }
        }
    }

    private fun validateForm(
        name: String,
        jumlahLatihan: String,
        jumlahRepetisi: String,
        details: List<String>
    ): Boolean {
        return name.isNotEmpty() &&
                jumlahLatihan.isNotEmpty() &&
                jumlahRepetisi.isNotEmpty() &&
                details.isNotEmpty()
    }
}