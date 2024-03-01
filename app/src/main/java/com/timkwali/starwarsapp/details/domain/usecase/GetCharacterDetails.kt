package com.timkwali.starwarsapp.details.domain.usecase

import android.util.Log
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetailsMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterDetails @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository
) {

    suspend operator fun invoke(characterId: String): Flow<CharacterDetails> = flow {
        characterDetailsRepository.getCharacterDetails(characterId).collect {
            emit(CharacterDetailsMapper().mapToDomain(it))
        }
    }
}
