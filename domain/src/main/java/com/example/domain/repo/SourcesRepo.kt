package com.example.domain.repo

import com.example.domain.entity.sourcesResponce.SourcesResponse

interface SourcesRepo {
    suspend fun getSourcesFromRemote(category:String):SourcesResponse
}