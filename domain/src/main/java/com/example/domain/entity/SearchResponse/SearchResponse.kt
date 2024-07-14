package com.example.domain.entity.SearchResponse

import com.example.domain.entity.newsResponce.News

data class SearchResponse(

    val totalResults: Int? = null,

    val articles: List<News?>? = null,

    val status: String? = null
)