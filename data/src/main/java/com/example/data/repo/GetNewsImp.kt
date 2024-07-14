package com.example.data.repo

import com.example.data.Constants
import com.example.data.remote.ApiService
import com.example.domain.entity.newsResponce.NewsResponse
import com.example.domain.repo.NewsRepo

class GetNewsImp(val apiService: ApiService):NewsRepo {
    override suspend fun getNewsFromRemote(sources: String): NewsResponse {
        return apiService.getNews(Constants.apiKey,sources)
    }


}