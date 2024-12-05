package com.asyfdzaky.uas_pppb_fitnes_app.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asyfdzaky.shareprefence.PrefManager
import com.asyfdzaky.uas_pppb_fitnes_app.Admin.AdminActivity
import com.asyfdzaky.uas_pppb_fitnes_app.HomeActivity
import com.asyfdzaky.uas_pppb_fitnes_app.Model.User.User
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiClient
import com.asyfdzaky.uas_pppb_fitnes_app.R
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var prefManager : PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val client = ApiClient.getInstance()

        with(binding){
            btnLogin.setOnClickListener{
                val response = client.getAllUsers()
                response.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.forEach { i ->
                                if(i.username == Username.text.toString() && i.password == Password.text.toString()){
                                    prefManager.setLoggedIn(true)
                                    prefManager.saveUsername(Username.text.toString())
                                    prefManager.savePassword(Password.text.toString())
                                    prefManager.saveRole(i.role)
                                    checkLoginStatus()
                                    finish()
                                }
                            }
                        } else {
                            Log.e("API Error", "Response not successful or body is null")
                        }
                    }
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Koneksi error ${t.toString()}", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
    fun checkLoginStatus(){
        if(prefManager.isLoggedIn()){
            if(prefManager.getRole() == "admin"){
                val intentToHome = Intent(this@LoginActivity, AdminActivity::class.java)
                startActivity(intentToHome)
            }else if(prefManager.getRole() == "user"){
                val intentToHome = Intent(this@LoginActivity,HomeActivity::class.java)
                startActivity(intentToHome)
            }
        }
    }
}

