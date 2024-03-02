package com.timkwali.starwarsapp.details.domain.usecase

import android.net.ConnectivityManager
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.isInternetAvailable
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetailsMapper
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

class GetSpecies @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
    private val connectivityManager: ConnectivityManager
) {
    suspend operator fun invoke(species: String): Flow<Resource<Species>> = channelFlow {
        try {
            if(!isInternetAvailable(connectivityManager)) {
                throw IOException()
            } else {
                characterDetailsRepository.getSpecies(species).collectLatest {
                    if(it is Resource.Success) {
                        send(Resource.Success(SpeciesMapper().mapToDomain(it.data)))
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