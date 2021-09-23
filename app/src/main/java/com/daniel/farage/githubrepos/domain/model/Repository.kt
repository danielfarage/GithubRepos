package com.daniel.farage.githubrepos.domain.model

import com.google.gson.annotations.SerializedName

data class Repository(
    val nameRepo: String,
    val stars: Int,
    val forkCount: Int,
    val authorPic: String,
    val authorName: String
)
