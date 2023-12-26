package com.talabalardaftari.tdcleanhilt.presentation.fragment.auth

import android.util.Patterns
import androidx.fragment.app.viewModels
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.snackbar
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.databinding.FragmentRegisterBinding
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewmodel : AuthViewModel by viewModels()
    private val shared by lazy {
        SharedPref(requireContext())
    }
    override fun onViewCreate() {
        observer()
        binding.register.setOnClickListener {
            if (validation()){
                shared.setEmail(binding.emailEditText.text.toString())
                shared.setName(binding.nameEditText.text.toString())
                viewmodel.sendEmail(binding.emailEditText.text.toString())
            }
        }
    }
    private fun observer(){
        viewmodel.sendEmail.observe(viewLifecycleOwner){state->
            when (state){
                is BaseNetworkResult.Error -> {
                    toast(state.message)
                    binding.progressRGG.hide()
                    binding.register.show()
                }
                is BaseNetworkResult.Loading -> {
                    binding.progressRGG.show()
                    binding.register.hide()
                }
                is BaseNetworkResult.Success -> {
                    binding.progressRGG.hide()
                    binding.register.show()
                    shared.setName(binding.nameEditText.text.toString())
                    shared.setEmail(binding.emailEditText.text.toString())
                    snackbar(state.data.toString(), binding.root)
                    navController.navigate(R.id.action_authFragment_to_confirmFragment)
                }
            }
        }
    }
    private fun validation(): Boolean {
        if (binding.nameEditText.text.toString() == "") {
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.nameTextInput)
            return false
        } else {
            if (binding.nameEditText.text.toString().length < 3) {
                YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.nameTextInput)
                toast("Ism kamida 3 ta belgidan iborat bo'lishi kerak")
                return false
            }
        }
        if (binding.emailEditText.text.toString() == "") {
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.phoneTextInput)
            return false
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString()).matches()) {
                YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.phoneTextInput)
                return false
            }
        }
        return true
    }
}