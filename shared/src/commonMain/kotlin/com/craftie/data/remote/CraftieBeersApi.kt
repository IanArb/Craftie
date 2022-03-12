package com.craftie.data.remote

import com.craftie.data.model.Beer
import com.craftie.data.model.Pagination
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.component.KoinComponent

class CraftieBeersApi(private val httpClient: HttpClient) : KoinComponent {

    companion object {
        private const val PAGE_KEY = "page"
        private const val PROVINCE_KEY = "province"
        private const val KEYWORD_KEY = "keyword"
    }

    suspend fun beersPageable(page: Int = 1): Pagination<Beer> {
        val response = httpClient.get(Endpoints.BEERS_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
        }
        return response.body()
    }

    suspend fun findBeer(id: String): Beer {
        val response = httpClient.get(Endpoints.BEERS_ENDPOINT.plus("/$id"))
        return response.body()
    }

    suspend fun beersByProvincePageable(page: Int = 1, province: String): Pagination<Beer> {
        val response =  httpClient.get(Endpoints.BEERS_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
            parameter(PROVINCE_KEY, province)
        }
        return response.body()
    }

    suspend fun featuredBeer(): Beer {
        val response = httpClient.get(Endpoints.BEERS_FEATURED_ENDPOINT)
        return response.body()
    }

    suspend fun findBeersByKeyword(keyword: String): List<Beer> {
        val response =  httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter(KEYWORD_KEY, keyword)
        }
        return response.body()
    }
}