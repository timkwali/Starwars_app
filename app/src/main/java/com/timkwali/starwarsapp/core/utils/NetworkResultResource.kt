package com.timkwali.starwarsapp.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


fun <T>Response<T>.handleResponse(): Flow<Resource<T?>> {
    return try {
        if(this.isSuccessful) {
            flowOf(Resource.Success(this.body()))
        } else {
            throw HttpException(this)
        }
    } catch (e: HttpException) {
        flowOf(Resource.Error(e.toErrorType()))
    } catch (e: IOException) {
        flowOf(Resource.Error(e.toErrorType()))
    } catch (e: Exception) {
        flowOf(Resource.Error(e.toErrorType()))
    }
}
