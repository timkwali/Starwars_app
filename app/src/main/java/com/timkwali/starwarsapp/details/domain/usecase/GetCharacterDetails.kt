package com.timkwali.starwarsapp.details.domain.usecase

import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetailsMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class GetCharacterDetails @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
) {
    suspend operator fun invoke(characterId: String): Flow<Resource<CharacterDetails>> = channelFlow {
        try {
            characterDetailsRepository.getCharacterDetails(characterId).collect {
                if(it is Resource.Success) {
                    send(Resource.Success(CharacterDetailsMapper().mapToDomain(it.data)))
                } else if(it is Resource.Error){
                    send(Resource.Error(error = it.error))
                }
            }
        } catch (e: Exception) {
            send(Resource.Error(error = e.toErrorType()))
        }
    }
}
