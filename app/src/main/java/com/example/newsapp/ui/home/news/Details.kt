package com.example.newsapp.ui.home.news

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDetailsBinding

class Details : AppCompatActivity() {
   lateinit var viewBinding:ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        loadDetails()
        moveToSourceUrl()

    }

    private fun moveToSourceUrl() {
        val postUrl=intent.getStringExtra("news_url")
        viewBinding.sourceUrl.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(postUrl))
            startActivity(browserIntent)
        }

    }

    private fun loadDetails(){
        val tittle=intent.getStringExtra("tittle")
        val description=intent.getStringExtra("description")
        val image=intent.getStringExtra("image")
        val time =intent.getStringExtra("time")
        val source =intent.getStringExtra("source")

        viewBinding.tittle.text=tittle
        viewBinding.description.text=description
        viewBinding.time.text=time
        viewBinding.source.text=source
        Glide.with(this).load(image)
            .placeholder(R.drawable.splash_logo)
            .into(viewBinding.imageView)
    }
}