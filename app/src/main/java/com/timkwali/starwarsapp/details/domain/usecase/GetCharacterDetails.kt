package com.timkwali.starwarsapp.details.domain.usecase

import android.net.ConnectivityManager
import com.timkwali.starwarsapp.core.utils.Resource
import com.timkwali.starwarsapp.core.utils.isInternetAvailable
import com.timkwali.starwarsapp.core.utils.toErrorType
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetails
import com.timkwali.starwarsapp.details.domain.model.details.CharacterDetailsMapper
import com.timkwali.starwarsapp.details.domain.repository.CharacterDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCharacterDetails @Inject constructor(
    private val characterDetailsRepository: CharacterDetailsRepository,
    private val connectivityManager: ConnectivityManager
) {
    suspend operator fun invoke(characterId: String): Flow<Resource<CharacterDetails>> = channelFlow {
        try {
            if(!isInternetAvailable(connectivityManager)) {
                throw IOException()
            } else {
                characterDetailsRepository.getCharacterDetails(characterId).collect {
                    if(it is Resource.Success) {
                        send(Resource.Success(CharacterDetailsMapper().mapToDomain(it.data)))
                    } else if(it is Resource.Error){
                        send(Resource.Error(error = it.error))
                    }
                }
            }
        } catch (e: IOException) {
            send(Resource.Error(e.toErrorType()))
        } catch (e: Exception) {
            send(Resource.Error(error = e.toErrorType()))
        }
    }
}
