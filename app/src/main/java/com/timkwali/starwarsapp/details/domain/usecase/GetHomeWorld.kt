package com.timkwali.starwarsapp.details.domain.usecase

import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorld
import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorldMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHomeWorld @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository
) {
    suspend operator fun invoke(homeWorldUrl: String): Flow<HomeWorld> = flow {
        characterDetailsRepository.getHomeWorld(homeWorldUrl).collect {
            emit(HomeWorldMapper().mapToDomain(it))
        }
    }
}