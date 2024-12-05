package com.asyfdzaky.uas_pppb_fitnes_app.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.asyfdzaky.uas_pppb_fitnes_app.DetailActivity
import com.asyfdzaky.uas_pppb_fitnes_app.HomeLatihanAdapter
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Latihan.Latihan
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiClient
import com.asyfdzaky.uas_pppb_fitnes_app.Network.ApiService
import com.asyfdzaky.uas_pppb_fitnes_app.R
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var executorService: ExecutorService
    private lateinit var client: ApiService
    private lateinit var latihanList: ArrayList<Latihan>
    private lateinit var adapterLatihan: HomeLatihanAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        latihanList = ArrayList()

        executorService = Executors.newSingleThreadExecutor()
        val client = ApiClient.getInstance()
        with(binding) {
            adapterLatihan = HomeLatihanAdapter(latihanList, client) { data ->

                val intent = Intent(requireActivity(), DetailActivity::class.java).apply {
                    putExtra("id", data.id)
                    putExtra("name", data.name)
                    putExtra("jumlahLatihan", data.jumlahLatihan)
                    putExtra("jumlahRepetisi", data.jumlahRepetisi)
                    putExtra("details1", data.details[0])
                    putExtra("details2", data.details[1])
                    putExtra("details3", data.details[2])
                    putExtra("details4", data.details[3])
                    putExtra("details5", data.details[4])
                }

                startActivity(intent)
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapterLatihan

            progressBar.visibility = android.view.View.VISIBLE
        }
        fetchLatihanList()

        return binding.root
    }

    private fun fetchLatihanList() {
        val client = ApiClient.getInstance()
        val response = client.getAllLatihan()

        response.enqueue(object : Callback<List<Latihan>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Latihan>>, response: Response<List<Latihan>>) {
                if (response.isSuccessful && response.body() != null) {
                    // Tambahkan data ke dalam list dan perbarui adapter
                    binding.progressBar.visibility = android.view.View.GONE
                    latihanList.clear()
                    latihanList.addAll(response.body()!!)
                    adapterLatihan.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memuat data latihan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Latihan>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}