package com.daniel.farage.githubrepos.domain.model

data class Repository(
    val nameRepo: String,
    val stars: Int,
    val forkCount: Int,
    val authorPic: String,
    val authorName: String
)
