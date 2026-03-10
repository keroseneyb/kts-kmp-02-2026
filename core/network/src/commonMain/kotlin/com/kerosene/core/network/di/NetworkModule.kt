package com.kerosene.core.network.di

import com.kerosene.core.auth.api.TokenProvider
import com.kerosene.core.network.ProjectApiService
import com.kerosene.core.network.CabinetProvider
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    fun HttpClientConfig<*>.commonConfig(json: Json, tokenProvider: TokenProvider?) {
        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message, tag = "HTTP Client")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpCallValidator) {
            validateResponse { response ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    tokenProvider?.clearTokens()
                }
            }
        }

        install(createClientPlugin("AuthPlugin") {
            onRequest { request, _ ->
                tokenProvider?.getAccessToken()?.let { tokenValue ->
                    request.header(HttpHeaders.Cookie, tokenValue)
                }
            }
        })
    }

    single(named("AuthClient")) {
        val tokenProvider = getOrNull<TokenProvider>()
        HttpClient {
            commonConfig(get(), tokenProvider)
            defaultRequest {
                url("https://auth.smartbotpro.ru/")
                header(HttpHeaders.Accept, ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    single(named("SProClient")) {
        val tokenProvider = getOrNull<TokenProvider>()
        HttpClient {
            commonConfig(get(), tokenProvider)

            defaultRequest {
                url("https://www.smartbotpro.ru/api/")
                header(HttpHeaders.Accept, ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    single { ProjectApiService(get(named("AuthClient")), get(named("SProClient")), get()) }
}
