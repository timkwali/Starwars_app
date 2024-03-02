package com.timkwali.starwarsapp.details.domain.usecase

import android.net.ConnectivityManager
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.isInternetAvailable
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorld
import com.timkwali.starwarsapp.details.domain.model.homeworld.HomeWorldMapper
import com.timkwali.starwarsapp.details.domain.model.species.Species
import com.timkwali.starwarsapp.details.domain.model.species.SpeciesMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class GetHomeWorld @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
    private val connectivityManager: ConnectivityManager
) {

    suspend operator fun invoke(homeWorldUrl: String): Flow<Resource<HomeWorld>> = channelFlow {
        try {
            if(!isInternetAvailable(connectivityManager)) {
                throw IOException()
            } else {
                characterDetailsRepository.getHomeWorld(homeWorldUrl).collectLatest {
                    if(it is Resource.Success) {
                        send(Resource.Success(HomeWorldMapper().mapToDomain(it.data)))
                    } else if(it is Resource.Error) {
                        send(Resource.Error(error = it.error))
                    }
                }
            }
        } catch (e: IOException) {
            send(Resource.Error(error = e.toErrorType()))
        }catch (e: Exception) {
            send(Resource.Error(error = e.toErrorType()))
        }
    }
}