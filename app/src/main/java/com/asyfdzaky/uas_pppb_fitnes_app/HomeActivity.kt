    package com.asyfdzaky.uas_pppb_fitnes_app

    import android.os.Bundle
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.navigation.findNavController
    import androidx.navigation.ui.setupWithNavController
    import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityHomeBinding

    class HomeActivity : AppCompatActivity() {
        private lateinit var binding: ActivityHomeBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)

            with(binding){
                val navController = findNavController(R.id.nav_host_fragment)
                bottomNavigationView.setupWithNavController(navController)


            }
        }
    }