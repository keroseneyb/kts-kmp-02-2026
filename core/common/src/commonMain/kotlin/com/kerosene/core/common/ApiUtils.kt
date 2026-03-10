package com.kerosene.core.common

import kotlin.coroutines.cancellation.CancellationException

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Constants {
    const val UNKNOWN_ERROR = "Unknown error"
}

fun Flow<String>.setupSearch(
    scope: CoroutineScope,
    debounceTime: Duration = 500.milliseconds,
    minQueryLength: Int = 0,
    onSearch: suspend (String) -> Unit
) {
    this.debounce(debounceTime)
        .map { it.trim() }
        .distinctUntilChanged()
        .filter { it.isEmpty() || it.length >= minQueryLength }
        .onEach { onSearch(it) }
        .launchIn(scope)
}

suspend inline fun <T> handleApiCall(
    crossinline apiCall: suspend () -> T,
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: suspend (T) -> Unit,
    crossinline onError: suspend (String) -> Unit
) {
    onLoading()
    runCatching {
        apiCall()
    }.fold(
        onSuccess = { onSuccess(it) },
        onFailure = { e ->
            if (e is CancellationException) throw e
            onError(e.message ?: Constants.UNKNOWN_ERROR)
        }
    )
}