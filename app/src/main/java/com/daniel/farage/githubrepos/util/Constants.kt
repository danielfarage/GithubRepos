package com.daniel.farage.githubrepos.util

object Constants {

    const val BASE_URL = "https://api.github.com/search/"

    object Params {
        const val QUERY_LANGUAGE = "q"
        const val QUERY_LANGUAGE_VALUE = "language:kotlin"
        const val QUERY_SORT = "sort"
        const val QUERY_SORT_VALUE = "stars"
        const val QUERY_PERPAGE = "per_page"
        const val QUERY_PERPAGE_VALUE = 10
        const val QUERY_PAGE = "page"
    }
}