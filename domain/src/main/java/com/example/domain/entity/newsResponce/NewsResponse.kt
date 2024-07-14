package com.example.domain.entity.newsResponce



data class NewsResponse(

	val totalResults: Int? = null,

	val articles: List<News?>? = null,

	val status: String? = null,
	val message: String? = null,
	val code: String? = null
)