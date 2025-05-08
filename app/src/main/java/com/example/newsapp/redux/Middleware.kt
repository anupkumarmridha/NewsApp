package com.example.newsapp.redux

import android.util.Log
import com.example.newsapp.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware

fun createNewsMiddleware(
    getNewsUseCase: GetNewsUseCase,
    coroutineScope: CoroutineScope
): Middleware<AppState> = { store ->
    { next ->
        { action ->
            val result = next(action)
            when (action) {
                is NewsAction.Loading -> {
                    coroutineScope.launch {
                        try {
                            // Use the query from the action
                            val articles = getNewsUseCase(action.query)
                            store.dispatch(NewsAction.NewsLoadedSuccess(articles))
                        } catch (e: Exception) {
                            store.dispatch(NewsAction.Error(e.message ?: "Unknown error"))
                        }
                    }
                }
            }
            result
        }
    }
}


val loggerMiddleware = middleware<AppState> { store, next, action ->
    Log.d("Redux", "▶ Dispatching $action")
    val result = next(action)
    Log.d("Redux", "✔ New state: ${store.state}")
    result
}

val crashReporter = middleware<AppState> { _, next, action ->
    try {
        next(action)
    } catch (e: Throwable) {
        // e.g. Crashlytics.logException(e)
        throw e
    }
}