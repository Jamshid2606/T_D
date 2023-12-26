package com.talabalardaftari.tdcleanhilt.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.models.SlideModel
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.snackbar
import com.talabalardaftari.tdcleanhilt.databinding.ActivitySplash2Binding

class SplashActivity2 : AppCompatActivity() {
    lateinit var binding:ActivitySplash2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.img_slide1))
        imageList.add(SlideModel(R.drawable.img_slide2))
        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setItemChangeListener(object :ItemChangeListener{
            override fun onItemChanged(position: Int) {
                if (position==1){
                    binding.boshlash.show()
                }else{
                    binding.boshlash.hide()
                }
            }
        })
        binding.boshlash.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}