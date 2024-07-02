package data.remote

import data.isDebug
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect fun httpClientEngine(): HttpClientEngine

fun createHttpClient(httpClientEngine: HttpClientEngine) = HttpClient(httpClientEngine) {
    expectSuccess = true
    install(Resources)
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Logging) {
        level = if (isDebug) LogLevel.ALL else LogLevel.NONE
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    HttpResponseValidator {
        //ketika ada error request non 200 akan di handle disini
        handleResponseExceptionWithRequest { exception, _ ->
            when(exception) {
                is ResponseException -> {
                    val responseException = exception.response.body<FailedResponse>()
                    throw ResponseException(
                        message = responseException.reason
                    )
                }
            }
        }
    }

    defaultRequest {
        url {
            host = "api.open-meteo.com"
            encodedPath = "v1/"
            protocol = URLProtocol.HTTPS
            contentType(ContentType.Application.Json)
        }
    }
}