package com.timkwali.starwarsapp.core.utils

import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

fun Int.toErrorType(): ErrorType {
    return when(this) {
        ErrorCodes.Http.ResourceNotFound   -> ErrorType.Api.NotFound
        ErrorCodes.Http.InternalServer     -> ErrorType.Api.Server
        ErrorCodes.Http.ServiceUnavailable -> ErrorType.Api.ServiceUnavailable
        else -> ErrorType.Unknown
    }
}

fun Throwable.toErrorType() = when (this) {
    is IOException -> ErrorType.Api.Network
    is EmptyResponseException -> ErrorType.Api.EmptyListError
    is HttpException -> when (code()) {
        ErrorCodes.Http.ResourceNotFound -> ErrorType.Api.NotFound
        ErrorCodes.Http.InternalServer -> ErrorType.Api.Server
        ErrorCodes.Http.ServiceUnavailable -> ErrorType.Api.ServiceUnavailable
        else -> ErrorType.Unknown
    }
    else -> ErrorType.Unknown
}