package com.timkwali.starwarsapp.details.domain.usecase

import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetailsMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class GetCharacterDetails @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository
) {
    suspend operator fun invoke(characterId: String): Resource<Flow<CharacterDetails>>  {
        return try {
            Resource.Success(
                characterDetailsRepository.getCharacterDetails(characterId).map {
                    CharacterDetailsMapper().mapToDomain(it) }
            )
        } catch (e: IOException) {
            Resource.Error(error = e.toErrorType())
        }catch (e: Exception) {
            Resource.Error(error = e.toErrorType())
        }
    }
}
