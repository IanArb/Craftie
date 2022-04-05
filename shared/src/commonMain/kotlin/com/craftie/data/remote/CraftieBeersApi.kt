package com.craftie.data.remote

import com.craftie.data.model.Beer
import com.craftie.data.model.JwtToken
import com.craftie.data.model.Login
import com.craftie.data.model.Pagination
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import kotlin.math.log

class CraftieBeersApi(
    private val httpClient: HttpClient,
    private val authenticationApi: CraftieAuthenticationApi,
) : KoinComponent {

    suspend fun beersPageable(page: Int = 1): Pagination<Beer> {
        val token = authenticationApi.login().token
        val response = httpClient.get(Endpoints.BEERS_ENDPOINT) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            parameter(PAGE_KEY, page)
        }
        return response.body()
    }

    suspend fun findBeer(id: String): Beer {
        val response = httpClient.get(Endpoints.BEERS_ENDPOINT.plus("/$id"))
        return response.body()
    }

    suspend fun beersByProvincePageable(page: Int = 1, province: String): Pagination<Beer> {
        val token = authenticationApi.login().token
        val response = httpClient.get(Endpoints.BEERS_ENDPOINT) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            parameter(PAGE_KEY, page)
            parameter(PROVINCE_KEY, province)
        }
        return response.body()
    }

    suspend fun featuredBeer(): Beer {
        val token = authenticationApi.login().token
        val response = httpClient.get(Endpoints.BEERS_FEATURED_ENDPOINT) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
        }
        return response.body()
    }

    suspend fun findBeersByKeyword(keyword: String): List<Beer> {
        val token = authenticationApi.login().token
        val response = httpClient.get(Endpoints.BEERS_KEYWORD_ENDPOINT) {
            headers {
                append(HttpHeaders.Authorization, token)
            }
            parameter(KEYWORD_KEY, keyword)
        }
        return response.body()
    }

    companion object {
        private const val PAGE_KEY = "page"
        private const val PROVINCE_KEY = "province"
        private const val KEYWORD_KEY = "keyword"
    }

}