package com.timkwali.starwarsapp.core.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
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

fun getHttpException(code: Int) = HttpException(Response.error<ResponseBody>(
    code,
    ResponseBody.create("plain/text".toMediaTypeOrNull(), "")
))

fun ErrorType.toException() = when(this) {
    is ErrorType.Api.Network -> IOException()
    is ErrorType.Api.EmptyListError -> EmptyResponseException()
    is ErrorType.Api.NotFound -> getHttpException(404)
    is ErrorType.Api.Server -> getHttpException(503)
    is ErrorType.Api.ServiceUnavailable -> getHttpException(501)
    else -> Exception()
}