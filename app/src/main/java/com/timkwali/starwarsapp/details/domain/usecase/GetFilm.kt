package com.timkwali.starwarsapp.details.domain.usecase

import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.film.Film
import com.timkwali.starwarsapp.details.domain.model.film.FilmMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetFilm @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
) {
    suspend operator fun invoke(filmUrl: String): Flow<Resource<Film>> = channelFlow {
        try {
            characterDetailsRepository.getFilm(filmUrl).collectLatest {
                if(it is Resource.Success) {
                    send(Resource.Success(FilmMapper().mapToDomain(it.data)))
                } else if(it is Resource.Error) {
                    send(Resource.Error(error = it.error))
                }
            }
        }catch (e: Exception) {
            send(Resource.Error(error = e.toErrorType()))
        }
    }
}