package com.example.domain.usecase

import com.example.domain.entity.sourcesResponce.SourcesResponse
import com.example.domain.repo.SourcesRepo

class GetSources(private val sourcesRepo:SourcesRepo) {
    suspend operator fun invoke(category: String)=sourcesRepo.getSourcesFromRemote(category)
}