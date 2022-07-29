package com.bcd.gifsearch.utilities

sealed class NetworkResource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : NetworkResource<T>(data)
    class Error<T>(error: Throwable? = null) : NetworkResource<T>(null, error)
    class Loading<T>() : NetworkResource<T>()
}