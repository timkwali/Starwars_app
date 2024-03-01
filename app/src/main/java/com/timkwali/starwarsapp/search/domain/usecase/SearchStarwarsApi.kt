package com.timkwali.starwarsapp.search.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.model.character.CharacterMapper
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SearchStarwarsApi @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(
        searchQuery: String
    ): Flow<Resource<PagingData<Character>>> = flow{
        try {
            searchRepository.searchStarwarsApi(searchQuery).collect {
                emit(Resource.Success(
                        it.map { CharacterMapper().mapToDomain(it) }
                ))
            }
        } catch (e: IOException) {
            emit(Resource.Error(error = e.toErrorType()))
        }catch (e: Exception) {
            emit(Resource.Error(error = e.toErrorType()))
        }
    }
}