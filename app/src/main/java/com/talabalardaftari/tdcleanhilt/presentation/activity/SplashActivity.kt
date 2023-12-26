package com.talabalardaftari.tdcleanhilt.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import com.talabalardaftari.tdcleanhilt.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    val sharedPref by lazy {
        SharedPref(this)
    }
    lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val anim = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {
                if (sharedPref.getToken()?.isNotEmpty() == true){
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                    finish()
                }
            }
            override fun onAnimationRepeat(p0: Animation?) {}

        })
        binding.img.startAnimation(anim)
    }
}