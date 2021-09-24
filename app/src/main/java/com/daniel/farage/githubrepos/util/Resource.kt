package com.daniel.farage.githubrepos.util

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val data: Exception) : Resource<Nothing>()
}
