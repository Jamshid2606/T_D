package com.talabalardaftari.tdcleanhilt.presentation.fragment.auth

import android.content.Intent
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.snackbar
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.databinding.FragmentAuthBinding
import com.talabalardaftari.tdcleanhilt.presentation.activity.MainActivity
import com.talabalardaftari.tdcleanhilt.presentation.adapters.ViewPagerAdapter
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    override fun onViewCreate() {
        initTab()
    }
    private fun initTab() {
        val adapter = ViewPagerAdapter(this@AuthFragment)
        binding.viewPager.adapter = adapter
        val pagesList = arrayListOf<String>()
        pagesList.add("Kirish")
        pagesList.add("Ro`yhatdan O`tish")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pagesList[position]
        }.attach()
    }
}