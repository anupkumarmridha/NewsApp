package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.GetNewsUseCase
import com.example.newsapp.redux.AppState
import com.example.newsapp.redux.NewsAction
import com.example.newsapp.redux.appReducer
import com.example.newsapp.redux.crashReporter
import com.example.newsapp.redux.createNewsMiddleware
import com.example.newsapp.redux.loggerMiddleware
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.createThreadSafeStore
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNews: GetNewsUseCase
) : ViewModel() {

    private val store = createThreadSafeStore(
        ::appReducer,
        AppState(),
        applyMiddleware(
            createNewsMiddleware(getNews, viewModelScope),
            loggerMiddleware,
            crashReporter
        )
    )

    private val _uiState = MutableStateFlow(store.state)
    val uiState: StateFlow<AppState> = _uiState

    init {
        // Subscribe store updates to StateFlow
        store.subscribe {
            _uiState.value = store.state
        }
        // Kick off initial load
        loadNews("General")
    }


    fun loadNews(query: String) {
        store.dispatch(NewsAction.Loading(query))
    }
}
