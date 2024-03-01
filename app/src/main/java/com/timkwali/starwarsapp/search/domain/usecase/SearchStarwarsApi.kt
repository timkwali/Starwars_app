package com.timkwali.starwarsapp.search.domain.usecase

import androidx.paging.PagingData
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class SearchStarwarsApi @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(searchQuery: String): Resource<Flow<PagingData<Character>>> {
        return try {
            Resource.Success(searchRepository.searchStarwarsApi(searchQuery))
        } catch (e: IOException) {
            Resource.Error(error = e.toErrorType())
        }catch (e: Exception) {
            Resource.Error(error = e.toErrorType())
        }
    }
}