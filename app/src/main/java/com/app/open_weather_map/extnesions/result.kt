package com.app.open_weather_map.extnesions

inline fun <T> Result<T>.onSuccess(func: (T) -> Unit): Result<T> = run {
    if (isSuccess) {
        func.invoke(getOrThrow())
    }
    this
}

@Suppress("unused")
inline fun <T> Result<T>.onError(func: (Throwable?) -> Unit): Result<T> = run {
    if (isFailure)
        func.invoke(exceptionOrNull())
    this
}

inline fun <T> Result<T>.onAny(func: () -> Unit): Result<T> = run {
    func.invoke()
    this
}
