package com.asyfdzaky.uas_pppb_fitnes_app.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.asyfdzaky.shareprefence.PrefManager
import com.asyfdzaky.uas_pppb_fitnes_app.R
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.FragmentAccountBinding
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.FragmentHomeBinding
import com.asyfdzaky.uas_pppb_fitnes_app.databinding.ItemDialogBinding
import java.util.concurrent.ExecutorService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:  FragmentAccountBinding
    private lateinit var prefManager: PrefManager



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
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        val prefManager = PrefManager.getInstance(requireContext())
        val username = prefManager.getUsername()
        val email = prefManager.getEmail()
        with(binding){

            tvNama.text = username
            tvRole.text = email

            btnLogout.setOnClickListener{
                showLogoutDialog()
            }

        }
        return (binding.root)
    }
    private fun showLogoutDialog() {
        // Inflate the custom dialog layout using ViewBinding
        val builder = AlertDialog.Builder(requireActivity())
        val inflate = requireActivity().layoutInflater
        val binding = ItemDialogBinding.inflate(inflate)
        val dialog = builder.setView(binding.root)
            .setCancelable(false) // Make dialog non-cancelable by tapping outside
            .create()

        with(binding){
            dialogTitle.text = "Logout"
            dialogMessage.text = "Apakah Anda yakin ingin logout?"
            btnConfirm.text = "Ya"
            btnConfirm.setOnClickListener {
                prefManager.clear()
                requireActivity().finish()
            }
            btnCancel.text = "Tidak"
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}