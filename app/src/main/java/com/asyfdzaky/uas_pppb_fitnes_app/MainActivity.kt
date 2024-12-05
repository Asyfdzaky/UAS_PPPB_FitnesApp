package com.asyfdzaky.uas_pppb_fitnes_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asyfdzaky.uas_pppb_fitnes_app.Login.LoginActivity
import com.asyfdzaky.uas_pppb_fitnes_app.Login.RegisterActivity
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            button.setOnClickListener {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
            button2.setOnClickListener {
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            }
        }
    }
}