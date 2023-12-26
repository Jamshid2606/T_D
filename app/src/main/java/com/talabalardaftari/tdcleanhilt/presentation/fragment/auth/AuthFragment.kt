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
    val viewmodel : AuthViewModel by viewModels()
    private var progressBarRg: ProgressBar?=null
    private var registerBtn: Button?=null
    private var progressBarLg:ProgressBar?=null
    private var loginBtn:Button?=null
    val shared by lazy {
        SharedPref(requireContext())
    }
    override fun onViewCreate() {
        registerBtn = requireActivity().findViewById(R.id.register)
        progressBarRg = requireActivity().findViewById(R.id.progressRGG)
        progressBarLg = requireActivity().findViewById(R.id.progressLGG)
        loginBtn = requireActivity().findViewById(R.id.login)
        observer()
        initTab()
    }

    private fun observer() {
        viewmodel.sendEmail.observe(viewLifecycleOwner){state->
            when (state){
                is BaseNetworkResult.Error -> {
                    toast(state.message)
                    registerBtn?.show()
                    progressBarRg?.hide()
                }
                is BaseNetworkResult.Loading -> {
                    registerBtn?.hide()
                    progressBarRg?.show()
                }
                is BaseNetworkResult.Success -> {
                    registerBtn?.show()
                    progressBarRg?.hide()

                    snackbar(state.data.toString(), binding.root)
                    navController.navigate(R.id.action_authFragment_to_confirmFragment)
                }
            }
        }
        viewmodel.login.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {
                    toast(state.message)
                    loginBtn?.show()
                    progressBarLg?.hide()
                }
                is BaseNetworkResult.Loading -> {
                    loginBtn?.hide()
                    progressBarLg?.show()
                }
                is BaseNetworkResult.Success -> {
                    loginBtn?.show()
                    progressBarLg?.hide()
                    if (state.data!=null){
                        shared.setToken(state.data.id_token)
                        snackbar("Successfully", binding.root)
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        requireActivity().startActivity(intent)
                    }
                }
            }
        }
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
        adapter.setRegisterBtnClickListener { progress, register, name, email ->
            shared.setEmail(email)
            shared.setName(name)
            viewmodel.sendEmail(email)
        }
        adapter.setLoginBtnClickListener { username, password, progress, login ->
            viewmodel.login(LoginRequest(password, username))
        }
    }
}