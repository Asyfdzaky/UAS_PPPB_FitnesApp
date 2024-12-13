package com.asyfdzaky.uas_pppb_fitnes_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Room.LatihanTable
import com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal.LatihanDao
import com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal.LatihanRoomDatabase
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityDetailBinding
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ItemDialogBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var executorService: ExecutorService
    private lateinit var LatihanDao: LatihanDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executorService = Executors.newSingleThreadExecutor()
        val latihanRoomDatabase = LatihanRoomDatabase.getDatabase(applicationContext)
        LatihanDao = latihanRoomDatabase?.latihanDao()!!

        with(binding) {

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

            ivBack.setOnClickListener {
                finish()
            }
            btnFavorite.setOnClickListener {
                showFavoriteDialog()
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

    private fun showFavoriteDialog() {
        val dialogBinding = ItemDialogBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()
        dialogBinding.dialogIcon.setImageResource(R.drawable.baseline_favorite_24)
        dialogBinding.dialogTitle.text = "Tambah ke Favorit"
        dialogBinding.dialogMessage.text =
            "Apakah Anda yakin ingin menambahkan latihan ini ke favorit?"

        dialogBinding.btnConfirm.setOnClickListener {
            // Insert the item into the database
            val latihan = LatihanTable(
                id = intent.getStringExtra("id").toString(),
                name = intent.getStringExtra("name").toString(),
                jumlahLatihan = intent.getStringExtra("jumlahLatihan").toString(),
                jumlahRepetisi = intent.getStringExtra("jumlahRepetisi").toString(),
                details = listOf(
                    intent.getStringExtra("details1").toString(),
                    intent.getStringExtra("details2").toString(),
                    intent.getStringExtra("details3").toString(),
                    intent.getStringExtra("details4").toString(),
                    intent.getStringExtra("details5").toString()
                )
            )
            insert(latihan)
            dialog.dismiss() // Dismiss the dialog after confirming
            Toast.makeText(this, "Latihan berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT)
                .show()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog if canceled
        }

        dialog.show() // Show the dialog
    }
}