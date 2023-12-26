package com.talabalardaftari.tdcleanhilt.presentation.adapters

import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talabalardaftari.tdcleanhilt.presentation.fragment.auth.LoginFragment
import com.talabalardaftari.tdcleanhilt.presentation.fragment.auth.RegisterFragment
import kotlin.math.log

class ViewPagerAdapter(fa:Fragment): FragmentStateAdapter(fa) {
    private lateinit var listenerRegBtn:((progress: ProgressBar, register: Button, name:String, email:String)->Unit)
    fun setRegisterBtnClickListener(listener:((progress: ProgressBar, register: Button, name:String, email:String)->Unit)){
        listenerRegBtn=listener
    }
    private lateinit var listenerLg:((username:String, password:String,progress:ProgressBar, login:Button) -> Unit)
    fun setLoginBtnClickListener(listener:((username:String, password:String,progress:ProgressBar, login:Button) -> Unit)){
        listenerLg = listener
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val loginFragment = LoginFragment()
                loginFragment.loginBtnClickListener { username, password, progress, login ->
                    listenerLg.invoke(username,password, progress, login)
                }
                loginFragment
            }
            else -> {
                val registerFragment = RegisterFragment()
                registerFragment.registerBtnClickListener{progress, register, name, email ->
                    listenerRegBtn.invoke(progress, register, name, email)
                }
                registerFragment
            }
        }
    }

    override fun getItemCount(): Int = 2
}