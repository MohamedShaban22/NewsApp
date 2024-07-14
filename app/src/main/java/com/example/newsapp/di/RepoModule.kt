package com.example.newsapp.di

import com.example.data.remote.ApiService
import com.example.data.repo.GetNewsImp
import com.example.data.repo.GetNewsSearchImp
import com.example.data.repo.GetSourcesImp
import com.example.domain.repo.NewsRepo
import com.example.domain.repo.SearchRepo
import com.example.domain.repo.SourcesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideNewsRepo(apiService: ApiService):NewsRepo{
        return GetNewsImp(apiService)
    }

    @Provides
    fun provideSourcesRepo(apiService: ApiService):SourcesRepo{
        return GetSourcesImp(apiService)
    }

    @Provides
    fun provideSearchRepo(apiService: ApiService):SearchRepo{
        return GetNewsSearchImp(apiService)
    }

}