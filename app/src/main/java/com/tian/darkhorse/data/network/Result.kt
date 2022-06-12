package com.tian.darkhorse.data.network

data class Result<out T>(val value: Any?) {

    val isSuccess: Boolean get() = value !is Failure

    val isFailure: Boolean get() = value is Failure

    fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    companion object {
        fun <T> success(value: T): Result<T> =
            Result(value)

        fun <T> failure(exception: Throwable): Result<T> =
            Result(Failure(exception))
    }

    internal class Failure(
        @JvmField
        val exception: Throwable
    ) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

fun <R, T> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when {
        isSuccess -> Result.success(transform(value as T))
        else -> Result(value)
    }
}

fun <R, T> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R {
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess(value as T)
        else -> onFailure(exception)
    }
}
