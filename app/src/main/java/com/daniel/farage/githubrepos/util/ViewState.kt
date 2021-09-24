package com.daniel.farage.githubrepos.util

import java.lang.Exception

sealed class ViewState {
    data class Loading(val isLoading: Boolean): ViewState()
    data class Error(val exception: Exception): ViewState()
    data class Navigate(val toDestination: Int): ViewState()
}
