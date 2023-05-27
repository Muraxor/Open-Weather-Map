package com.app.open_weather_map.data.network.interceptors

import com.app.open_weather_map.data.network.exceptions.*
import com.app.open_weather_map.utils.client.NetworkMessageResolver
import com.app.open_weather_map.utils.observers.httperrors.HttpErrorManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.net.ConnectException
import java.net.HttpURLConnection.*
import java.net.SocketTimeoutException
import javax.inject.Inject

class HttpErrorsInterceptor @Inject constructor(
    private val httpErrorManager: HttpErrorManager,
    private val networkMessageResolver: NetworkMessageResolver
) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val request = chain.request()

        return wrapTry {
            val response = chain.proceed(request)

            with(response) {
                return if (isSuccessful) {
                    this
                } else {
                    val message = networkMessageResolver.resolveServerMessage(this)
                    val exception = when (code) {
                        in HTTP_INTERNAL_ERROR..HTTP_VERSION -> ServerErrorException(code, message)
                        HTTP_UNAUTHORIZED -> UnauthorizedException(code, message)
                        else -> RawErrorException(code, message)
                    }
                    sendAndThrow(exception)
                }
            }
        }
    }

    // TODO: make factory to get ready exception
    private inline fun wrapTry(block: () -> Response) = try {
        block()
    } catch (e: SocketTimeoutException) {
        val message = networkMessageResolver.getMessageByException(ServerTimeoutException::class)
        ServerTimeoutException(message = message).run { sendAndThrow(this) }
    } catch (e: ConnectException) {
        val message = networkMessageResolver.getMessageByException(ServerTimeoutException::class)
        ConnectionRefusedException(message = message).run { sendAndThrow(this) }
    }

    private fun sendAndThrow(e: NetworkException): Nothing {
        httpErrorManager.send(e)
        throw e
    }
}
