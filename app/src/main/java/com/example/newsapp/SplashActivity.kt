package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.newsapp.databinding.ActivitySplashBinding
import com.example.newsapp.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        Handler().postDelayed({
            val mIntent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 2000)
    }

}