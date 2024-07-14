package com.example.data.remote

import com.example.data.Constants
import com.example.domain.entity.SearchResponse.SearchResponse
import com.example.domain.entity.newsResponce.NewsResponse
import com.example.domain.entity.sourcesResponce.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
       @GET("v2/everything")
    suspend fun getNews(

           @Query("apiKey")apiKey:String= Constants.apiKey,
           @Query("sources")sources:String

    ): NewsResponse


    @GET("v2/top-headlines/sources")
    suspend  fun getResources(

        @Query("apiKey")apiKey:String= Constants.apiKey,
        @Query("category") category :String
    ): SourcesResponse

    @GET("v2/everything")
    suspend fun search(

        @Query("apiKey")apiKey:String= Constants.apiKey,
        @Query("q")q:String

    ):SearchResponse
}