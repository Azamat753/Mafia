package com.lawlett.mafia.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.lawlett.mafia.R
import com.lawlett.mafia.core.BaseFragment
import com.lawlett.mafia.core.showToast
import com.lawlett.mafia.data.Prefs
import com.lawlett.mafia.databinding.AuthFragmentBinding
import com.lawlett.mafia.databinding.HomeFragmentBinding
import java.util.*
import kotlin.collections.HashMap

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment),
    View.OnClickListener {
    override fun getViewModule(): HomeViewModel =
        ViewModelProvider(this).get(HomeViewModel::class.java)

    override fun getViewBinding(): HomeFragmentBinding? {
        return rootView?.let { rootView -> HomeFragmentBinding.bind(rootView) }
    }

    override fun setUpView() {
        setClickListeners()
    }

    private fun setClickListeners() {
        binding?.lobbi1?.setOnClickListener(this)
        binding?.lobbi2?.setOnClickListener(this)
        binding?.lobbi3?.setOnClickListener(this)
    }

    override fun setUpViewModelObs() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lobbi1 -> enterInLobbi(
                binding?.lobbi1?.text.toString()
                    .toLowerCase(Locale.ROOT)
                    .trim()
            )
            R.id.lobbi2 -> enterInLobbi(
                binding?.lobbi2?.text.toString()
                    .toLowerCase(Locale.ROOT)
                    .trim()
            )
            R.id.lobbi3 -> enterInLobbi(
                binding?.lobbi3?.text.toString()
                    .toLowerCase(Locale.ROOT)
                    .trim()
            )
        }
    }

    private fun enterInLobbi(lobbi: String) {
        val tokenID = Prefs(requireContext()).getToken
        mViewModule!!.enterLobbi(lobbi,tokenID)
        mViewModule!!.isSuccess.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            requireContext().showToast("Success")
        })
    }

}