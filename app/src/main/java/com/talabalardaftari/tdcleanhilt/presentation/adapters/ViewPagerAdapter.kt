package com.talabalardaftari.tdcleanhilt.presentation.adapters

import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talabalardaftari.tdcleanhilt.presentation.fragment.auth.LoginFragment
import com.talabalardaftari.tdcleanhilt.presentation.fragment.auth.RegisterFragment
import kotlin.math.log

class ViewPagerAdapter(fa:Fragment): FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { LoginFragment() }
            else -> { RegisterFragment() }
        }
    }

    override fun getItemCount(): Int = 2
}