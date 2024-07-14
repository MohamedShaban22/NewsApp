package com.example.domain.usecase

import com.example.domain.repo.NewsRepo

class GetNews(val newsRepo: NewsRepo) {
    suspend operator fun invoke( sources: String)=newsRepo.getNewsFromRemote(sources)
}