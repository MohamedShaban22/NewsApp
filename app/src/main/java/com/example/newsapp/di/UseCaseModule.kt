package com.example.newsapp.di

import com.example.domain.repo.NewsRepo
import com.example.domain.repo.SearchRepo
import com.example.domain.repo.SourcesRepo
import com.example.domain.usecase.GetNews
import com.example.domain.usecase.GetNewsSearch
import com.example.domain.usecase.GetSources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideNewsUseCase(newsRepo: NewsRepo):GetNews{
        return GetNews(newsRepo)

    }
    @Provides
    fun provideSourcesUseCase(sourcesRepo: SourcesRepo):GetSources{
        return GetSources(sourcesRepo)
    }

    @Provides
    fun provideSearchUseCase(searchRepo: SearchRepo):GetNewsSearch{
        return GetNewsSearch(searchRepo)
    }
}