package com.asyfdzaky.uas_pppb_fitnes_app.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asyfdzaky.uas_pppb_fitnes_app.FavoriteDetailActivity
import com.asyfdzaky.uas_pppb_fitnes_app.LatihanFavoriteAdapater
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Room.LatihanTable
import com.asyfdzaky.uas_pppb_fitnes_app.R
import com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal.LatihanDao
import com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal.LatihanRoomDatabase
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.FragmentBookmarkBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentBookmarkBinding
    private lateinit var adapter : LatihanFavoriteAdapater
    private lateinit var  latihanDao : LatihanDao
    private lateinit var executorService: ExecutorService

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
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        executorService = Executors.newSingleThreadExecutor()
        val db = LatihanRoomDatabase.getDatabase(requireContext())
        latihanDao = db!!.latihanDao()
        setupRecyclerView()



        return binding.root

    }

    private fun deleteFavorite(latihan: LatihanTable) {
        executorService.execute {
            latihanDao.delete(latihan)
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Berhasil menghapus latihan dari favorit", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = LatihanFavoriteAdapater(requireContext(),
            deleteAction = { data ->
                deleteFavorite(data) // Handle delete action
            },
            LatihanDetail = { data ->
                // Navigate to detail activity
                val intent = Intent(requireContext(), FavoriteDetailActivity::class.java).apply {
                    putExtra("id", data.id)
                    putExtra("name", data.name)
                    putExtra("jumlahLatihan", data.jumlahLatihan)
                    putExtra("jumlahRepetisi", data.jumlahRepetisi)
                    data.details.forEachIndexed { index, detail ->
                        putExtra("details${index + 1}", detail)
                    }
                }
                startActivity(intent)
            }
        )
        binding.recyclerView.adapter = adapter

        // Amati perubahan data dari LiveData
        latihanDao.getAllLatihan().observe(viewLifecycleOwner) { dataList ->
            adapter.submitList(dataList) // Perbarui dataset
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookmarkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}