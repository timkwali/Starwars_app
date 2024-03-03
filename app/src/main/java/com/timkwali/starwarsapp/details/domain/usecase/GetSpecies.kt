package com.timkwali.starwarsapp.details.domain.usecase

import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.details.domain.model.species.SpeciesMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetSpecies @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
) {
    suspend operator fun invoke(species: String): Flow<Resource<Species>> = channelFlow {
        try {
            characterDetailsRepository.getSpecies(species).collectLatest {
                if(it is Resource.Success) {
                    send(Resource.Success(SpeciesMapper().mapToDomain(it.data)))
                } else if(it is Resource.Error) {
                    send(Resource.Error(error = it.error))
                }
            }
        }catch (e: Exception) {
            send(Resource.Error(error = e.toErrorType()))
        }
    }
}