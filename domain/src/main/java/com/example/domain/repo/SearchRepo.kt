package com.example.domain.repo

import com.example.domain.entity.SearchResponse.SearchResponse

interface SearchRepo {
    suspend fun getNewsSearchFromRemote(q:String): SearchResponse
}