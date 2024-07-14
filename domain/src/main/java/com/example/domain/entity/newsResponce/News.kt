package com.example.domain.entity.newsResponce

import android.os.Parcelable
import com.example.domain.entity.sourcesResponce.Source

data class News(

	val publishedAt: String? = null,

	val author: String? = null,

	val urlToImage: String? = null,

	val description: String? = null,

	val source: Source? = null,

	val title: String? = null,

	val url: String? = null,

	val content: String? = null
)