package com.example.domain.repo

import com.example.domain.entity.newsResponce.NewsResponse

interface NewsRepo {
    suspend fun getNewsFromRemote(sources:String):NewsResponse


}