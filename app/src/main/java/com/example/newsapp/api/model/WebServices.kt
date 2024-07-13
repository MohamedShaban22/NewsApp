package com.example.newsapp.api.model

import com.example.newsapp.api.model.SearchResponse.SearchResponse
import com.example.newsapp.api.model.newsResponce.NewsResponse
import com.example.newsapp.api.model.sourcesResponce.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources")
    fun getResources(

        @Query("apiKey")apiKey:String=ApiConstants.apiKey,
        @Query("category") category :String
    ):Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(

        @Query("apiKey")apiKey:String=ApiConstants.apiKey,
        @Query("sources")sources:String

    ):Call<NewsResponse>

    @GET("v2/everything")
    fun search(

        @Query("apiKey")apiKey:String=ApiConstants.apiKey,
        @Query("q")q:String

    ):Call<SearchResponse>
}