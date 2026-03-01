package com.kerosene.core.common

import kotlin.coroutines.cancellation.CancellationException

object Constants {
    const val UNKNOWN_ERROR = "Unknown error"
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