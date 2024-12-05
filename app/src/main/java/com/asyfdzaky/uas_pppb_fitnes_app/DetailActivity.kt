package com.asyfdzaky.uas_pppb_fitnes_app

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Room.LatihanTable
import com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal.LatihanDao
import com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal.LatihanRoomDatabase
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityDetailBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var executorService: ExecutorService
    private lateinit var LatihanDao : LatihanDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executorService = Executors.newSingleThreadExecutor()
        val latihanRoomDatabase = LatihanRoomDatabase.getDatabase(applicationContext)
        LatihanDao = latihanRoomDatabase?.latihanDao()!!

        with(binding){

            tvTitle.text = intent.getStringExtra("name")
            tvWorkoutTitle1.text = intent.getStringExtra("details1")
            tvWorkoutTitle2.text = intent.getStringExtra("details2")
            tvWorkoutTitle3.text = intent.getStringExtra("details3")
            tvWorkoutTitle4.text = intent.getStringExtra("details4")
            tvWorkoutTitle5.text = intent.getStringExtra("details5")
            tvWorkoutDesc1.text = intent.getStringExtra("jumlahRepetisi")
            tvWorkoutDesc2.text = intent.getStringExtra("jumlahRepetisi")
            tvWorkoutDesc3.text = intent.getStringExtra("jumlahRepetisi")
            tvWorkoutDesc4.text = intent.getStringExtra("jumlahRepetisi")
            tvWorkoutDesc5.text = intent.getStringExtra("jumlahRepetisi")

            ivBack.setOnClickListener{
                finish()
            }
            btnFavorite.setOnClickListener {
                val latihan = LatihanTable(
                    id = intent.getStringExtra("id").toString(), // Pass the id as String
                    name = intent.getStringExtra("name").toString(),
                    jumlahLatihan = intent.getStringExtra("jumlahLatihan").toString(),
                    jumlahRepetisi = intent.getStringExtra("jumlahRepetisi").toString(),
                    details = listOf(
                        intent.getStringExtra("details1").toString(),
                        intent.getStringExtra("details2").toString(),
                        intent.getStringExtra("details3").toString(),
                        intent.getStringExtra("details4").toString(),
                        intent.getStringExtra("details5").toString()
                    ) // Convert list to comma-separated string
                )
                insert(latihan)
            }
        }
    }
    private fun insert(latihan: LatihanTable) {
        // Insert the latihan into the database in a background thread
        executorService.execute {
            // Insert latihan into the database
            LatihanDao.insert(latihan)

            // Move observation to the main thread
            runOnUiThread {
                // Observe the LiveData on the main thread
                LatihanDao.getAllLatihan().observe(this@DetailActivity, { latihanList ->
                    // This will be triggered when the data changes or is initially available
                    latihanList?.forEach { latihan ->
                        Log.d("Latihan Inserted", "ID: ${latihan.id}, Name: ${latihan.name}")
                    }
                })
            }
        }
    }
}