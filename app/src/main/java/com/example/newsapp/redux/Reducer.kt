package com.example.newsapp.redux

fun appReducer(state: AppState, action: Any): AppState {
    return when (action) {
        is NewsAction.Loading -> state.copy(isLoading = true, error = null)
        is NewsAction.NewsLoadedSuccess -> state.copy(
            articles = action.articles,
            isLoading = false,
            error = null
        )
        is NewsAction.Error -> state.copy(
            isLoading = false,
            error = action.message
        )
        else -> state
    }
    }
