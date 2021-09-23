package com.daniel.farage.githubrepos.util

object Constants {

    const val BASE_URL = "https://api.github.com/search"

    object Params {
        const val QUERY_LANGUAGE = "q"
        const val QUERY_LANGUAGE_VALUE = "language:kotlin"
        const val QUERY_SORT = "sort"
        const val QUERY_SORT_VALUE = "stars"
        const val QUERY_PAGE = "page"
    }
}