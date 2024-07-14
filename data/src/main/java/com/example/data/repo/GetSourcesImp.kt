package com.example.data.repo

import com.example.data.Constants
import com.example.data.remote.ApiService
import com.example.domain.entity.sourcesResponce.SourcesResponse
import com.example.domain.repo.SourcesRepo

class GetSourcesImp (val apiService: ApiService):SourcesRepo{
    override suspend fun getSourcesFromRemote(category: String): SourcesResponse {
        return apiService.getResources(Constants.apiKey,category)
    }
}