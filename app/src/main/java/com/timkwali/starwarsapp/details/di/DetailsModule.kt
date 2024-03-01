package com.timkwali.starwarsapp.details.di

import com.timkwali.starwarsapp.core.data.remote.StarwarsApi
import com.timkwali.starwarsapp.details.data.repository.CharacterDetailsRepositoryImpl
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import com.timkwali.starwarsapp.search.data.repository.SearchRepositoryImpl
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailsModule {

    @Provides
    @Singleton
    fun provideCharacterDetailsRepository(starwarsApi: StarwarsApi): CharacterDetailsRepository {
        return CharacterDetailsRepositoryImpl(starwarsApi)
    }
}