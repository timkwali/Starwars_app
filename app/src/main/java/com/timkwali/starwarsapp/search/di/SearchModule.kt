package com.timkwali.starwarsapp.search.di

import com.timkwali.starwarsapp.core.data.api.StarwarsApi
import com.timkwali.starwarsapp.search.data.repository.SearchRepositoryImpl
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchRepository(starwarsApi: StarwarsApi): SearchRepository {
        return SearchRepositoryImpl(starwarsApi)
    }
}