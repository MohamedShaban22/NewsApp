package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.entity.SearchResponse.SearchResponse
import com.example.domain.repo.SearchRepo
import com.example.domain.usecase.GetNewsSearch

class GetNewsSearchImp(val apiService: ApiService): SearchRepo{
    override suspend fun getNewsSearchFromRemote(q: String): SearchResponse {
        return apiService.search(q=q)
    }
}