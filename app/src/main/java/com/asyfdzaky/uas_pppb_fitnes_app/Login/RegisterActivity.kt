package com.asyfdzaky.uas_pppb_fitnes_app.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asyfdzaky.uas_pppb_fitnes_app.Model.User.User
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiClient
import com.asyfdzaky.uas_pppb_fitnes_app.R
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol Register
        binding.btnRegister.setOnClickListener {
            val username = binding.Username.text.toString()
            val email = binding.Email.text.toString()
            val password = binding.Password.text.toString()
            val confirmPassword = binding.ConfirmPassword.text.toString()

            if (validateForm(username, email, password, confirmPassword)) {
                // Kirim permintaan untuk mengecek apakah username sudah ada
                val apiService = ApiClient.getInstance()
                val response = apiService.getAllUsers()

                response.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        if (response.isSuccessful && response.body() != null) {
                            val users = response.body()
                            // Mengecek apakah username sudah terdaftar
                            val existingUser = users?.find { it.username == username }
                            val existingEmail = users?.find { it.email == email }
                            if (existingUser != null || existingEmail != null) {
                                // Username sudah ada, beri tahu pengguna
                                Toast.makeText(this@RegisterActivity, "Username or email already exists", Toast.LENGTH_SHORT).show()
                            } else {
                                // Username belum ada, lanjutkan dengan pendaftaran
                                val user = User(
                                    id = null,
                                    username = username,
                                    email = email,
                                    password = password,
                                    role = "user" // Default role "user"
                                )
                                createUser(user)
                            }
                        } else {
                            Toast.makeText(this@RegisterActivity, "Failed to fetch users", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please fill out the form correctly!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk validasi form
    private fun validateForm(username: String, email: String, password: String, confirmPassword: String): Boolean {
        return username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword
    }

    // Fungsi untuk membuat user baru
    private fun createUser(user: User) {
        val apiService = ApiClient.getInstance()
        val call = apiService.createUser(user)

        // Melakukan request API untuk membuat user
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    // Jika berhasil, arahkan ke LoginActivity
                    Toast.makeText(this@RegisterActivity, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    // Jika gagal
                    Toast.makeText(this@RegisterActivity, "Failed to register user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Jika gagal terhubung ke API
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

