package com.asyfdzaky.uas_pppb_fitnes_app.Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asyfdzaky.uas_pppb_fitnes_app.R
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityAdminDetailBinding

class AdminDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding) {
            val name = intent.getStringExtra("name")
            val jumlahWo = intent.getStringExtra("jumlahWo")
            val jumlahRep = intent.getStringExtra("jumlahRepetisi")
            val detailWo = intent.getStringExtra("details1")
            val detailWo2 = intent.getStringExtra("details2")
            val detailWo3 = intent.getStringExtra("details3")
            val detailWo4 = intent.getStringExtra("details4")
            val detailWo5 = intent.getStringExtra("details5")

            tvTitle.text = name
            tvWorkoutTitle1.text = detailWo
            tvWorkoutTitle2.text = detailWo2
            tvWorkoutTitle3.text = detailWo3
            tvWorkoutTitle4.text = detailWo4
            tvWorkoutTitle5.text = detailWo5
            tvWorkoutDesc1.text = jumlahRep
            tvWorkoutDesc2.text = jumlahRep
            tvWorkoutDesc3.text = jumlahRep
            tvWorkoutDesc4.text = jumlahRep
            tvWorkoutDesc5.text = jumlahRep

            ivBack.setOnClickListener {
                finish()
            }

        }
    }
}