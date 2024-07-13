package com.example.newsapp.ui

import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsapp.R

@BindingAdapter(value=["app:url","app:placeholder"], requireAll = false)
fun bindingAdapterUsingUrl(imageview:ImageView,url:String?,placeholder:Drawable?){
    Glide.with(imageview)
        .load(url)
        .placeholder(placeholder)
        .into(imageview)
}