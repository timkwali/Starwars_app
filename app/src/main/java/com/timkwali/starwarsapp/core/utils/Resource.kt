package com.timkwali.starwarsapp.core.utils

sealed class Resource<out T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val error: ErrorType): Resource<T>()
}