package com.azizutku.movie.core.common.network

import com.azizutku.movie.core.common.network.NetworkException.Companion.CODE_HTTP_EXCEPTION
import com.azizutku.movie.core.common.network.NetworkException.Companion.CODE_IO_EXCEPTION
import com.azizutku.movie.core.common.network.NetworkException.Companion.CODE_OTHER_EXCEPTION
import com.azizutku.movie.core.common.network.NetworkException.Companion.CODE_PARSING_EXCEPTION
import com.azizutku.movie.core.common.network.NetworkException.Companion.CODE_TIMEOUT_EXCEPTION
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

@Serializable
data class NetworkException(
    @SerialName("status_message")
    override val message: String? = null,
    @SerialName("status_code")
    val code: Int,
) : Exception(message) {

    companion object {
        const val CODE_PARSING_EXCEPTION = 65_535
        const val CODE_IO_EXCEPTION = 65_534
        const val CODE_HTTP_EXCEPTION = 65_533
        const val CODE_TIMEOUT_EXCEPTION = 65_532
        const val CODE_OTHER_EXCEPTION = 65_531

        fun getFromThrowable(throwable: Throwable): NetworkException = when (throwable) {
            is SocketTimeoutException -> NetworkException(code = CODE_TIMEOUT_EXCEPTION)
            is IOException -> NetworkException(code = CODE_IO_EXCEPTION)
            is HttpException -> NetworkException(code = CODE_HTTP_EXCEPTION)
            else -> NetworkException(throwable.localizedMessage, CODE_OTHER_EXCEPTION)
        }
    }
}

enum class GeneralNetworkExceptionCode(val code: Int) {
    PARSING_EXCEPTION(CODE_PARSING_EXCEPTION),
    IO_EXCEPTION(CODE_IO_EXCEPTION),
    HTTP_EXCEPTION(CODE_HTTP_EXCEPTION),
    TIMEOUT_EXCEPTION(CODE_TIMEOUT_EXCEPTION),
    OTHER_EXCEPTION(CODE_OTHER_EXCEPTION);

    companion object {
        fun getFromCode(code: Int): GeneralNetworkExceptionCode? =
            GeneralNetworkExceptionCode.values().find { it.code == code }
    }
}
