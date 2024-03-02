package com.timkwali.starwarsapp.search.domain.usecase

import android.net.ConnectivityManager
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.timkwali.starwarsapp.core.utils.Constants.PER_PAGE
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.isInternetAvailable
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.search.data.pagination.CharactersPagingSource
import com.timkwali.starwarsapp.search.domain.model.character.Character
import com.timkwali.starwarsapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class SearchStarwarsApi @Inject constructor(
    private val searchRepository: SearchRepository,
    private val connectivityManager: ConnectivityManager
) {
    operator fun invoke(searchQuery: String): Resource<Flow<PagingData<Character>>> {
        return try {
            if(!isInternetAvailable(connectivityManager)) {
                throw IOException()
            } else {
                Resource.Success(
                    Pager(
                        config = PagingConfig(
                            pageSize = PER_PAGE,
                            prefetchDistance = 4,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = {
                            CharactersPagingSource(
                                searchQuery = searchQuery,
                                searchRepository = searchRepository
                            )
                        }
                    ).flow
                )
            }
        } catch (e: IOException) {
            Resource.Error(error = e.toErrorType())
        }catch (e: Exception) {
            Resource.Error(error = e.toErrorType())
        }
    }
}