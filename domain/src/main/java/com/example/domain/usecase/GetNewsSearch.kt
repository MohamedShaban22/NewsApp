package com.example.domain.usecase

import com.example.domain.repo.SearchRepo

class GetNewsSearch(val searchRepo: SearchRepo) {
    suspend operator fun invoke(q:String)=searchRepo.getNewsSearchFromRemote(q)

}