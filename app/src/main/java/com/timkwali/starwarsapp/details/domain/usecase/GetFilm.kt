package com.timkwali.starwarsapp.details.domain.usecase

import android.util.Log
import com.timkwali.starwarsapp.details.domain.model.film.Film
import com.timkwali.starwarsapp.details.domain.model.film.FilmMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilm @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository
) {
    suspend operator fun invoke(filmUrl: String): Flow<Film> = flow {
        characterDetailsRepository.getFilm(filmUrl).collect {
            Log.d("-098o4j", "---->$it")
            emit(FilmMapper().mapToDomain(it))
        }
    }
}