package com.azizutku.movie.core.network

import com.azizutku.movie.core.common.network.NetworkException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.serialization.json.Json

@Suppress("UnsafeCallOnNullableType")
class ResultCall<T>(private val delegate: Call<T>) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    callback.onResponse(
                        this@ResultCall,
                        if (response.isSuccessful) {
                            Response.success(
                                response.code(),
                                Result.success(response.body()!!)
                            )
                        } else {
                            Response.success(
                                Result.failure(
                                    runCatching {
                                        Json.decodeFromString<NetworkException>(response.errorBody()!!.string())
                                    }.getOrDefault(
                                        NetworkException(code = NetworkException.CODE_PARSING_EXCEPTION)
                                    )
                                )
                            )
                        }
                    )
                }

                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            Result.failure(NetworkException.getFromThrowable(throwable))
                        )
                    )
                }
            }
        )
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun execute(): Response<Result<T>> = Response.success(
        Result.success(delegate.execute().body()!!)
    )

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun clone(): Call<Result<T>> = ResultCall(delegate.clone())

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
