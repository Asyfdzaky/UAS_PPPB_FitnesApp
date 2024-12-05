package com.asyfdzaky.uas_pppb_fitnes_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityFavoriteDetailBinding

class FavoriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

            ivBack.setOnClickListener{
                finish()
            }
            val name = intent.getStringExtra("name")
            val details1 = intent.getStringExtra("details1")
            val details2 = intent.getStringExtra("details2")
            val details3 = intent.getStringExtra("details3")
            val details4 = intent.getStringExtra("details4")
            val details5 = intent.getStringExtra("details5")
            val jumlahRepetisi = intent.getStringExtra("jumlahRepetisi")
            tvTitle.text = name
            tvWorkoutTitle1.text = details1
            tvWorkoutTitle2.text = details2
            tvWorkoutTitle3.text = details3
            tvWorkoutTitle4.text = details4
            tvWorkoutTitle5.text = details5
            tvWorkoutDesc1.text = jumlahRepetisi
            tvWorkoutDesc2.text = jumlahRepetisi
            tvWorkoutDesc3.text = jumlahRepetisi
            tvWorkoutDesc4.text = jumlahRepetisi
            tvWorkoutDesc5.text = jumlahRepetisi


        }


    }
}