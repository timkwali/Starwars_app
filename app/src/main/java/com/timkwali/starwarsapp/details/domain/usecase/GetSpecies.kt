package com.timkwali.starwarsapp.details.domain.usecase

import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.details.domain.model.species.SpeciesMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSpecies @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository
) {
    suspend operator fun invoke(species: String): Flow<Species> = flow {
        characterDetailsRepository.getSpecies(species).collect {
            emit(SpeciesMapper().mapToDomain(it))
        }
    }
}