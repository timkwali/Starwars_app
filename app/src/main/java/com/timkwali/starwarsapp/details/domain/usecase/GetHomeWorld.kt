package com.timkwali.starwarsapp.details.domain.usecase

import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorld
import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorldMapper
import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.details.domain.model.species.SpeciesMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class GetHomeWorld @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository
) {

    suspend operator fun invoke(homeWorldUrl: String): Resource<Flow<HomeWorld>> {
        return try {
            Resource.Success(
                characterDetailsRepository.getHomeWorld(homeWorldUrl).map {
                    HomeWorldMapper().mapToDomain(it)
                }
            )
        } catch (e: IOException) {
            Resource.Error(error = e.toErrorType())
        }catch (e: Exception) {
            Resource.Error(error = e.toErrorType())
        }
    }
}