package com.talabalardaftari.tdcleanhilt.presentation.fragment.auth

import android.content.Intent
import androidx.fragment.app.viewModels
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.snackbar
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.databinding.FragmentLoginBinding
import com.talabalardaftari.tdcleanhilt.presentation.activity.MainActivity
import com.talabalardaftari.tdcleanhilt.presentation.activity.SplashActivity2
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewmodel : AuthViewModel by viewModels()
    private val shared by lazy {
        SharedPref(requireContext())
    }
    override fun onViewCreate() {
        observer()
        binding.login.setOnClickListener {
            if (validation()){
                viewmodel.login(LoginRequest(
                    username = binding.usernameEditText.text.toString(),
                    password = binding.passwordEditText.text.toString()
                ))
            }
        }
    }
    private fun validation():Boolean{
        if (binding.usernameEditText.text==null){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.usernameEditText)
            return false
        }else if (binding.passwordEditText.text==null){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.passwordEditText)
            return false
        }
        return true
    }
    private fun observer() {
        viewmodel.login.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {
                    toast(state.message)
                    binding.progressLGG.hide()
                    binding.login.show()
                }
                is BaseNetworkResult.Loading -> {
                    binding.progressLGG.show()
                    binding.login.hide()
                }
                is BaseNetworkResult.Success -> {
                    binding.progressLGG.hide()
                    binding.login.show()
                    if (state.data!=null){
                        shared.setToken(state.data.id_token)
                        snackbar("Successfully", binding.root)
                        val intent = Intent(requireActivity(), SplashActivity2::class.java)
                        requireActivity().startActivity(intent)
                    }
                }
            }
        }
    }
}