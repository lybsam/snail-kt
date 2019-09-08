package com.pluto.snail.ext.net

import kotlinx.coroutines.Deferred


suspend fun <T> Deferred<T>.awaitOrError(): Result<T> {
    return try {
        Result.of(await())
    } catch (e: Exception) {
        Result.of(e)
    }
}