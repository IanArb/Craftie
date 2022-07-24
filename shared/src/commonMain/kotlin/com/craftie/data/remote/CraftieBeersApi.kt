package com.craftie.data.remote

import com.craftie.data.model.*
import com.craftie.data.settings.SettingsRepository
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import kotlin.math.log

class CraftieBeersApi(
    private val httpClient: HttpClient,
    settingsRepository: SettingsRepository,
) : KoinComponent {

    private val token = settingsRepository.token()

    private val baseUrl = settingsRepository.baseUrl()

    suspend fun beersPageable(page: Int = 1): Pagination<Beer> {
        val endpoint = baseUrl.plus(Endpoints.BEERS_ENDPOINT)
        val response = httpClient.get(endpoint) {
            headers {
                append(HttpHeaders.Authorization, "$BEARER_VALUE $token")
            }
            parameter(PAGE_KEY, page)
        }
        return response.body()
    }

    suspend fun findBeer(id: String): Beer {
        val endpoint = baseUrl.plus(Endpoints.BEERS_ENDPOINT.plus("/$id"))
        val response = httpClient.get(endpoint) {
            headers {
                append(HttpHeaders.Authorization, "$BEARER_VALUE $token")
            }
        }
        return response.body()
    }

    suspend fun beersByProvincePageable(page: Int = 1, province: String): Pagination<Beer> {
        val endpoint = baseUrl.plus(Endpoints.BEERS_ENDPOINT)
        val response = httpClient.get(endpoint) {
            headers {
                append(HttpHeaders.Authorization, "$BEARER_VALUE $token")
            }
            parameter(PAGE_KEY, page)
            parameter(PROVINCE_KEY, province)
        }
        return response.body()
    }

    suspend fun featuredBeer(): Beer {
        val endpoint = baseUrl.plus(Endpoints.BEERS_FEATURED_ENDPOINT)
        val response = httpClient.get(endpoint) {
            headers {
                append(HttpHeaders.Authorization, "$BEARER_VALUE $token")
            }
        }
        return response.body()
    }

    suspend fun findBeersByKeyword(keyword: String): List<Beer> {
        val endpoint = baseUrl.plus(Endpoints.BEERS_KEYWORD_ENDPOINT)
        val response = httpClient.get(endpoint) {
            headers {
                append(HttpHeaders.Authorization, "$BEARER_VALUE $token")
            }
            parameter(KEYWORD_KEY, keyword)
        }
        return response.body()
    }

    companion object {
        private const val PAGE_KEY = "page"
        private const val PROVINCE_KEY = "province"
        private const val KEYWORD_KEY = "keyword"
        private const val BEARER_VALUE = "Bearer"
    }

}